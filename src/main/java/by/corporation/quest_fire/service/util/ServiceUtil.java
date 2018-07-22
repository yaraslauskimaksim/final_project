package by.corporation.quest_fire.service.util;

public final class ServiceUtil {

    private ServiceUtil(){}

    public static int getNumberOfPage(int numberOfRecords, int itemsPerPage){
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / itemsPerPage);
        return numberOfPages;
    }
}
