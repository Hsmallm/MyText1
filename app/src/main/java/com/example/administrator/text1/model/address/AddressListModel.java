package com.example.administrator.text1.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hzhm on 2016/8/20.
 */
public class AddressListModel {

    public String addressId;

    public String name;

    @SerializedName("default")
    @Expose
    public boolean isDefault;

    public String phone;

    public String provinceCode;

    public String provinceName;

    public String cityCode;

    public String cityName;

    public String districtCode;

    public String districtName;

    public String address;

    public String idNumber;
}
