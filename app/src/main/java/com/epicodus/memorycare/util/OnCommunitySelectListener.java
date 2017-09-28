package com.epicodus.memorycare.util;

import com.epicodus.memorycare.models.Patient;
import java.util.ArrayList;

public interface OnCommunitySelectListener {
    public void onRestaurantSelected(Integer position, ArrayList<Patient> restaurants, String source);

}
