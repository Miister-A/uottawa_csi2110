/*
➥Name : Alae Boufarrachene
➥Program : Computer Engineering, 
➥Student number : 300188539
➥Course : CSI2110-D
➥Assignment number : #2
➥Academic year : 2021-2022
*/

import java.util.*;

/**
* @author Alae Boufarrachene
*/
public class A2 {

    /**
    * Helper method used to merge the busy time intervals
    */
    private static TimeInterval[] mergeTimeIntervals(TimeInterval[] busyIntervals) {

        if (busyIntervals.length==0) { //Edge case : The entered array is empty 
            System.out.println("You have entered an empty array !");
            return busyIntervals;
        }

        List<TimeInterval> busyIntervalsList = Arrays.asList(busyIntervals); //Converts the array of time intervals into a list to facilitate manipulation
        List<TimeInterval> mergedIntervalList = new ArrayList<TimeInterval>(); //List used to store the merged intervals

        mergedIntervalList.add(busyIntervalsList.get(0)); //Adds the first busy interval to the merged intervals

        int index=1; //Since we already stored the first element (index=0), we have to start at the second element (index=1)
        while (index<busyIntervals.length) {

            int
            tempStart = busyIntervalsList.get(index).getIntervalStart(), //Start time of the current interval
            tempEnd1 = busyIntervalsList.get(index).getIntervalEnd(), //End time of the current interval
            tempEnd2 = mergedIntervalList.get(mergedIntervalList.size()-1).getIntervalEnd(); //End time of the last interval in the merged list (when index=1, it's the same as the current)

            if (tempStart<=tempEnd2) { //Base case #1 : Start time of the current interval is inferior (or equal) to the end time of the last interval in the merged list
                int newLastInterval = Math.max(tempEnd1, tempEnd2); //Max method with the endtime of the current interval and the current endtime of the last interval in the merged list
                mergedIntervalList.get(mergedIntervalList.size()-1).setIntervalEnd(newLastInterval); //Changes the end of the last interval in the merged list
            }

            else { //Base case #2 : Otherwise creates a new interval with start time and end time of the current interval
                mergedIntervalList.add(new TimeInterval(tempStart, tempEnd1));
            }

            index++;
        }

        TimeInterval[] mergedIntervals = new TimeInterval[mergedIntervalList.size()];
        mergedIntervals = mergedIntervalList.toArray(mergedIntervals); //Converts the list back into an array

        return mergedIntervals;
    }
    
    /**
    * determine a sequence of time intervals where the group can hold meetings.
    */    
    public static void freeTimesToMeet(int T, TimeInterval[] busyIntervals) {

        busyIntervals = mergeTimeIntervals(busyIntervals); //Merges the busy time intervals by making a call to a helper method
        List<TimeInterval> busyIntervalsList = Arrays.asList(busyIntervals); //Convers the merged array into a merged list
        
        if (busyIntervalsList.isEmpty()) { //Edge case #1 : The list of busy intervals is empty
            System.out.println("You have entered an empty list !");
            return;
        }

        if (T<1) { //Edge case #2 : The number T is equal to zero (or negative)
            System.out.println("T has to be strictly superior to zero !");
            return;
        }

        List<TimeInterval> freeIntervals = new ArrayList<TimeInterval>(); //List of the available hours

        for (int i=0 ; i<busyIntervalsList.size() ; i++) {
            int tempStart;
            int tempEnd;

            /**
             *Base case #1 : 0 isn't a busy time (0 =/= start time of the first element in the busyIntervals array)
            */
            if (busyIntervalsList.get(i).equals(busyIntervalsList.get(0)) && busyIntervalsList.get(0).getIntervalStart()>0) {
                TimeInterval zeroStartTimeInterval = new TimeInterval(0, busyIntervalsList.get(i).getIntervalStart());
                freeIntervals.add(zeroStartTimeInterval);
            }
            
            /**
             *Base case #2 : T is superior to the endtime of the last busy interval
            */
            if (busyIntervalsList.get(i).getIntervalEnd()<=T && busyIntervalsList.get(i).equals(busyIntervalsList.get(busyIntervalsList.size()-1))) {
                TimeInterval maxEndTimeInterval = new TimeInterval(busyIntervalsList.get(busyIntervalsList.size()-1).getIntervalEnd(), T);
                freeIntervals.add(maxEndTimeInterval);
                break;
            }

            /**
             *Base case #3 : Standard procedure of taking the extremes of two busy intervals to create a free interval
            */
            else {
                tempStart = busyIntervalsList.get(i).getIntervalEnd(); //End time of current interval becomes the start time of the free slot
                tempEnd = busyIntervalsList.get(i+1).getIntervalStart(); //Start time of the next interval becomes the end time of the free slot
                TimeInterval tempFreeTimeInterval = new TimeInterval(tempStart,tempEnd);
                freeIntervals.add(tempFreeTimeInterval);
            }
        } 

        if (freeIntervals.get(freeIntervals.size()-1).getIntervalStart()==T && freeIntervals.get(freeIntervals.size()-1).getIntervalEnd()==T) {
            freeIntervals.remove(freeIntervals.size()-1);
        }

        System.out.println(freeIntervals);

    }
     
    public static void main(String[] arg) {

        TimeInterval[] busyIntervals = new TimeInterval[6];

        TimeInterval interval1 = new TimeInterval(1,9);
        TimeInterval interval2 = new TimeInterval(2,5);
        TimeInterval interval3 = new TimeInterval(4,10);
        TimeInterval interval4 = new TimeInterval(15,20);
        TimeInterval interval5 = new TimeInterval(20,25);
        TimeInterval interval6 = new TimeInterval(30,45);

        busyIntervals[0] = interval1;    
        busyIntervals[1] = interval2;
        busyIntervals[2] = interval3;
        busyIntervals[3] = interval4;
        busyIntervals[4] = interval5;
        busyIntervals[5] = interval6;
        System.out.println("T = 50");
        System.out.println("Busy time intervals :[1,9], [2,5], [4,10], [15,20], [20,25], [30,45]");
        System.out.print("Free time intervals :");
        freeTimesToMeet(50,busyIntervals);

    }
}