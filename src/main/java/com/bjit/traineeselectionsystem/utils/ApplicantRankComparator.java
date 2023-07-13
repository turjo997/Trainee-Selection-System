package com.bjit.traineeselectionsystem.utils;

import com.bjit.traineeselectionsystem.model.ApplicantRank;

import java.util.Comparator;

public class ApplicantRankComparator implements Comparator<ApplicantRank> {
    @Override
    public int compare(ApplicantRank a1, ApplicantRank a2) {
        // Compare based on marks in descending order
        return Double.compare(a2.getMarks(), a1.getMarks());
    }
}
