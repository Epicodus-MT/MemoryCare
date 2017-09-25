package com.epicodus.memorycare.services;

    import com.epicodus.memorycare.Constants;
    import com.epicodus.memorycare.models.Patient;

    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.HttpUrl;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import java.io.IOException;
    import java.util.ArrayList;
    import okhttp3.Response;
    import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
    import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class YelpService {

    public static void findPatient(String location, Callback callback) {
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
//        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        urlBuilder.addQueryParameter("categories", "health");
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Constants.YELP_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Patient> processResults(Response response) {
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject patientJSON = businessesJSON.getJSONObject(i);
                    String name = patientJSON.getString("name");
                    String phone = patientJSON.optString("display_phone", "Phone not available");
                    String website = patientJSON.getString("url");
                    double rating = patientJSON.getDouble("rating");
                    String imageUrl = patientJSON.getString("image_url");
                    JSONObject coordinates = patientJSON.getJSONObject("coordinates");
                    double latitude = coordinates.getDouble("latitude");
                    double longitude = coordinates.getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = patientJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.get(y).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = patientJSON.getJSONArray("categories");

                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONObject(0).getString("title"));
                    }
                    Patient patient = new Patient(name, phone, website, rating,
                            imageUrl, address, latitude, longitude, categories);
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
