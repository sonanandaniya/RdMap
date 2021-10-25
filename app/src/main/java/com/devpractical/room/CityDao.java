package com.devpractical.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {


    @Query("SELECT * FROM CityData")
    List<CityData> getAll();

    @Insert
    void insertAll(CityData... cities);

    @Insert()
    void insert(CityData cities);

    @Delete
    void delete(CityData city);


    @Delete
    void reset(List<CityData> cityList);

    @Query("Update CityData SET locationName = :cityNamed ,latitude = :lat ,longitude = :lng ,distance = :distance,startPoint = :startPoint  WHERE ID = :cId")
    void update(int cId, String cityNamed, String lat, String lng, double distance, String startPoint);


}
