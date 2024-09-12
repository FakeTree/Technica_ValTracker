package com.example.technica_valtracker;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

/// Validates given strings / objects to ensure they are legal
public class Validation {

    /// REGEX ///

    // Email Address
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Password (8 Chars long, 1 cap 1 numeral)
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    // Riot ID
    private static final String RIOT_ID_REGEX = "^(.*?)#(.+)$";
    private static final Pattern RIOT_ID_PATTERN = Pattern.compile(RIOT_ID_REGEX);

    // Riot ID Split
    private static final String RIOT_ID_REGEX_SPLIT = "^(.*?)(#(.*))?$";
    private static final Pattern RIOT_ID_PATTERN_SPLIT = Pattern.compile(RIOT_ID_REGEX_SPLIT);






    /// VALIDATION METHODS ///

    // Validate Email
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    // Validate Password
    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // Validate Riot ID
    public static boolean isRiotIDValid(String riotID) {
        System.out.println(riotID);
        System.out.println("Checking if valid!");
        if (riotID == null) {
            return false;
        }
        System.out.println(RIOT_ID_PATTERN.matcher(riotID).matches());
        return RIOT_ID_PATTERN.matcher(riotID).matches();
    }

    // Split Riot ID into username and tagline
    public static String[] splitRiotID(String riotID) {
        String[] result = new String[2]; // [0] -> username, [1] -> tagline
        if (riotID != null) {
            var matcher = RIOT_ID_PATTERN_SPLIT.matcher(riotID);
            if (matcher.matches()) {
                result[0] = matcher.group(1); // Username
                result[1] = matcher.group(3); // Tagline (can be null if not present)
            }
        }
        return result;
    }

    // Riot account exists?
    public static String puuidGet(String riotId) throws IOException {

        // Split the Riot ID into username and tagline
        String[] splitRiotID = Validation.splitRiotID(riotId);
        String userName = splitRiotID[0];
        String tagLine = splitRiotID[1]; // May be null if not provided

        ResponseBody responseBody = getAccountByRiotId(userName,tagLine);

        if (responseBody.isError()) {
            // The RiotID doesnt exist or there is an API issue (Check your key!)
            System.out.println("Error: " + responseBody.getMessage());
            return null;
        } else {
            // The RiotID has been found!
            JSONObject jsonObject = new JSONObject(responseBody.getJson());
            // Extract the PUUID
            return jsonObject.getString("puuid");

        }

    }

    public static ResponseBody getAccountByRiotId(String userName, String tagLine) throws IOException {
        String json;
        String requestUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + userName + "/" + tagLine;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.ANNETTE_RIOT_KEY)
                .url(requestUrl)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }

        return new ResponseBody(json, false);

    }



}

