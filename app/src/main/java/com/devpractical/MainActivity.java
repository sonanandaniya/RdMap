package com.devpractical;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devpractical.databinding.ActivityMainBinding;
import com.devpractical.dialog.FilterDialog;
import com.devpractical.map.ViewMapActivity;
import com.devpractical.utils.SortPlaces;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.model.Place;

import com.devpractical.room.RoomDb;
import com.devpractical.room.CityData;
import com.devpractical.utils.Logger;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static int AUTOCOMPLETE_REQUEST_CODE_UPDATE = 2;

    List<CityData> locationsList = new ArrayList<>();
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> ActivityResultLauncher2;

    LocationsAdapter locationsAdapter;
    ActivityMainBinding binding;
    RoomDb database;
    RecyclerViewClickListener listener;
    LinearLayoutManager linearLayoutManager;
    private double destlat, destLon;
    private String selectedFilter = "";
    LatLng latLng;
    private int aId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSearchLocationView();
        setIconToBlack();
        init();

    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Logger.d("Place: " + place.getName() + ", " + place.getId());

                if (!place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("")) {
                    CityData cityData = new CityData();
                    cityData.setLocationName(place.getName());
                    LatLng destinationLatLng = place.getLatLng();
                    if (destinationLatLng != null) {
                        destlat = destinationLatLng.latitude;
                        destLon = destinationLatLng.longitude;

                        cityData.setLatitude(String.valueOf(destlat));
                        cityData.setLongitude(String.valueOf(destLon));

                        if (locationsList.size() > 0) {
                            double lat1 = Double.parseDouble(locationsList.get(0).getLatitude());
                            double lon1 = Double.parseDouble(locationsList.get(0).getLongitude());
                            double lat2 = destlat;
                            double lon2 = destlat;

                            double distanceToPlace1 = SortPlaces.distance(lat2, lon2, lat1, lon1);
                            cityData.setDistance(distanceToPlace1);
                            cityData.setStartPoint(locationsList.get(0).getLocationName());

                            if (locationsList != null && locationsList.size() > 0) {
                                binding.tvShowPath.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvShowPath.setVisibility(View.GONE);
                            }
                        }
                        database.cityDao().insert(cityData);
                        Logger.d("latlng: " + String.valueOf(destlat) + ", " + String.valueOf(destLon));


                        locationsList.clear();
                        locationsList.addAll(database.cityDao().getAll());
                        locationsAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.no_proper_address_found), Toast.LENGTH_SHORT);
                    }


                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Logger.d("status_message" + status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_UPDATE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Logger.d("Place: " + place.getName() + ", " + place.getId());


                if (!place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("")) {


                    LatLng destinationLatLng = place.getLatLng();
                    if (destinationLatLng != null) {
                        destlat = destinationLatLng.latitude;
                        destLon = destinationLatLng.longitude;


                        if (locationsList.size() > 0) {
                            double lat1 = Double.parseDouble(locationsList.get(0).getLatitude());
                            double lon1 = Double.parseDouble(locationsList.get(0).getLongitude());
                            double lat2 = destlat;
                            double lon2 = destlat;

                            double distanceToPlace1 = SortPlaces.distance(lat2, lon2, lat1, lon1);

                            Logger.d("latlng: " + String.valueOf(destlat) + ", " + String.valueOf(destLon));
                            database.cityDao().update(aId, place.getName(), String.valueOf(destlat), String.valueOf(destLon), distanceToPlace1, locationsList.get(0).getLocationName());
                            locationsList.clear();
                            locationsList.addAll(database.cityDao().getAll());
                            locationsAdapter.notifyDataSetChanged();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.no_proper_address_found), Toast.LENGTH_SHORT);
                    }


                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Logger.d("status_message" + status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

*/

    private void init() {
        database = RoomDb.getInstance(this);
        locationsList = database.cityDao().getAll();

        Logger.d("dblistis " + new Gson().toJson(locationsList));
        binding.tvAddLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent1 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(MainActivity.this);
                someActivityResultLauncher.launch(intent1);

            }
        });
        binding.toolbar.tvToolbarMainDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.cityDao().reset(locationsList);
                locationsList.clear();
                locationsList.addAll(database.cityDao().getAll());
                locationsAdapter.notifyDataSetChanged();
            }
        });
        binding.tvShowPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationsList = sortLocations(locationsList, Double.parseDouble(locationsList.get(0).getLatitude()), Double.parseDouble(locationsList.get(0).getLongitude()));

                Intent i = new Intent(MainActivity.this, ViewMapActivity.class);
                i.putExtra("locationsList", (Serializable) locationsList);
                startActivity(i);
            }
        });

        binding.toolbar.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterDialog(MainActivity.this, selectedFilter, new FilterDialog.ButtonClickListener() {
                    @Override
                    public void onAddlicked(String methodis) {
                        selectedFilter = methodis;
                        if (locationsList != null && locationsList.size() > 1) {
                            latLng = new LatLng(Double.parseDouble(locationsList.get(0).getLatitude()), Double.parseDouble(locationsList.get(0).getLongitude()));
                            for (CityData p : locationsList) {
                                Logger.d("Places before sorting" + "Place: " + p.getLocationName());
                            }

                            locationsList = sortLocations(locationsList, Double.parseDouble(locationsList.get(0).getLatitude()), Double.parseDouble(locationsList.get(0).getLongitude()));
                            Logger.d("allascendingDatais " + new Gson().toJson(locationsList));

                            if (methodis.equalsIgnoreCase("1")) {
                                Collections.reverse(locationsList);
                                for (CityData p : locationsList) {
                                    Logger.d("Places after sorting" + "Place: " + p.getLocationName());
                                }
                            }
                            Logger.d("alldatais " + new Gson().toJson(locationsList));

                            locationsAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onClear() {
                        locationsList.clear();
                        locationsList.addAll(database.cityDao().getAll());
                        locationsAdapter.notifyDataSetChanged();


                    }
                }).show(getSupportFragmentManager(), "");
            }
        });

        setRecyclerView();

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            if (result.getResultCode() == RESULT_OK) {
                                Place place = Autocomplete.getPlaceFromIntent(data);

                                Logger.d("Place: " + place.getName() + ", " + place.getId());


                                if (!place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("")) {

                                    CityData cityData = new CityData();

                                    cityData.setLocationName(place.getName());

                                    LatLng destinationLatLng = place.getLatLng();
                                    if (destinationLatLng != null) {
                                        destlat = destinationLatLng.latitude;
                                        destLon = destinationLatLng.longitude;

                                        cityData.setLatitude(String.valueOf(destlat));
                                        cityData.setLongitude(String.valueOf(destLon));


                                        if (locationsList.size() > 0) {
                                            double lat1 = Double.parseDouble(locationsList.get(0).getLatitude());
                                            double lon1 = Double.parseDouble(locationsList.get(0).getLongitude());
                                            double lat2 = destlat;
                                            double lon2 = destlat;

                                            double distanceToPlace1 = SortPlaces.distance(lat2, lon2, lat1, lon1);
                                            cityData.setDistance(distanceToPlace1);
                                            cityData.setStartPoint(locationsList.get(0).getLocationName());


                                        }
                                        database.cityDao().insert(cityData);
                                        Logger.d("latlng: " + String.valueOf(destlat) + ", " + String.valueOf(destLon));


                                        locationsList.clear();
                                        locationsList.addAll(database.cityDao().getAll());
                                        locationsAdapter.notifyDataSetChanged();

                                        if (locationsList != null && locationsList.size() > 0) {
                                            binding.tvShowPath.setVisibility(View.VISIBLE);
                                        } else {
                                            binding.tvShowPath.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Toast.makeText(MainActivity.this, getString(R.string.no_proper_address_found), Toast.LENGTH_SHORT);
                                    }


                                }

                            } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                                // TODO: Handle the error.
                                Status status = Autocomplete.getStatusFromIntent(data);
                                Logger.d("status_message" + status.getStatusMessage());
                            } else if (result.getResultCode() == RESULT_CANCELED) {
                                // The user canceled the operation.
                            }
                            return;


                        }
                    }
                });

        ActivityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Place place = Autocomplete.getPlaceFromIntent(result.getData());

                            Logger.d("Place: " + place.getName() + ", " + place.getId());


                            if (!place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("") && !place.getName().equals("")) {


                                LatLng destinationLatLng = place.getLatLng();
                                if (destinationLatLng != null) {
                                    destlat = destinationLatLng.latitude;
                                    destLon = destinationLatLng.longitude;


                                    if (locationsList.size() > 0) {
                                        double lat1 = Double.parseDouble(locationsList.get(0).getLatitude());
                                        double lon1 = Double.parseDouble(locationsList.get(0).getLongitude());
                                        double lat2 = destlat;
                                        double lon2 = destlat;

                                        double distanceToPlace1 = SortPlaces.distance(lat2, lon2, lat1, lon1);

                                        Logger.d("latlng: " + String.valueOf(destlat) + ", " + String.valueOf(destLon));
                                        database.cityDao().update(aId, place.getName(), String.valueOf(destlat), String.valueOf(destLon), distanceToPlace1, locationsList.get(0).getLocationName());
                                        locationsList.clear();
                                        locationsList.addAll(database.cityDao().getAll());
                                        locationsAdapter.notifyDataSetChanged();



                                    }

                                    if (locationsList != null && locationsList.size() > 0) {
                                        binding.tvShowPath.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.tvShowPath.setVisibility(View.GONE);
                                    }

                                } else {
                                    Toast.makeText(MainActivity.this, getString(R.string.no_proper_address_found), Toast.LENGTH_SHORT);
                                }


                            }

                        } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                            // TODO: Handle the error.
                            Status status = Autocomplete.getStatusFromIntent(result.getData());
                            Logger.d("status_message" + status.getStatusMessage());
                        } else if (result.getResultCode() == RESULT_CANCELED) {
                            // The user canceled the operation.
                        }
                        return;

                    }
                });

    }

    private void setRecyclerView() {

        if (locationsList != null && locationsList.size() > 0) {
            binding.tvShowPath.setVisibility(View.VISIBLE);
        } else {
            binding.tvShowPath.setVisibility(View.GONE);
        }
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvCityListing.setLayoutManager(linearLayoutManager);
        locationsAdapter = new LocationsAdapter(MainActivity.this, locationsList, new LocationsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(int tag, int pos, Object data) {
                if (tag == -1) {


                    CityData cityData1 = locationsList.get(pos);
                    database.cityDao().delete(cityData1);
                    locationsList.remove(pos);
                    locationsAdapter.notifyItemRemoved(pos);
                    locationsAdapter.notifyItemRangeChanged(pos, locationsList.size());
                    if (locationsList != null && locationsList.size() > 0) {
                        binding.tvShowPath.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvShowPath.setVisibility(View.GONE);
                    }

                } else {
                    aId = tag;
                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                            .build(MainActivity.this);
                    ActivityResultLauncher2.launch(intent);
                }

            }
        });

        binding.rvCityListing.setAdapter(locationsAdapter);


    }


    private void setSearchLocationView() {

        Places.initialize(getApplicationContext(), getString(R.string.MAP_KEY));
        PlacesClient placesClient = Places.createClient(this);

    }


    public static List<CityData> sortLocations(List<CityData> locations, final double myLatitude, final double myLongitude) {
        Comparator comp = new Comparator<CityData>() {
            @Override
            public int compare(CityData o, CityData o2) {
                float[] result1 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, Double.parseDouble(o.getLatitude()), Double.parseDouble(o.getLongitude()), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, Double.parseDouble(o2.getLatitude()), Double.parseDouble(o2.getLongitude()), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };

        Collections.sort(locations, comp);
        return locations;
    }


    public void setIconToBlack() {
        //setStatusBarColor(R.color.color_FFFFFF);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public interface RecyclerViewClickListener {
        void onClick(int tag, int pos, Object data);
    }


}