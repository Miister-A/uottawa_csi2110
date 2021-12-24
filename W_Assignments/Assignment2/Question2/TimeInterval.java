/**
* @author Alae Boufarrachene
*/

/**
* Nested custom-made class used to encapsulate the implementation 
* This sub-class helps us achieve the following : int[][] -> BusyTimeInterval[]
*/
public class TimeInterval {

    public int intervalStart;
    public int intervalEnd;

    public TimeInterval(int start, int end) {
        this.intervalStart = start;
        this.intervalEnd = end;
    }

    public int getIntervalStart() {
        return intervalStart;
    }

    public int getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalStart(int intervalStart) {
        this.intervalStart = intervalStart;
    }

    public void setIntervalEnd(int intervalEnd) {
        this.intervalEnd = intervalEnd;
    } 

    @Override
    public String toString() {
        String toStringOutput = "["+this.intervalStart+","+this.intervalEnd+"]";
        return toStringOutput;
    }

}
