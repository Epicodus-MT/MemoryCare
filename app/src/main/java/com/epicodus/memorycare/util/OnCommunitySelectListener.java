package com.epicodus.memorycare.util;

import com.epicodus.memorycare.models.Community;
import java.util.ArrayList;

public interface OnCommunitySelectListener {
    public void onCommunitySelected(Integer position, ArrayList<Community> communities, String source);

}
