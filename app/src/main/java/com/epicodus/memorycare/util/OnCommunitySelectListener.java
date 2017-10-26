package com.epicodus.memorycare.util;

import com.epicodus.memorycare.models.Patient;
import java.util.ArrayList;

public interface OnCommunitySelectListener {
    public void onCommunitySelected(Integer position, ArrayList<Patient> communities, String source);

}
