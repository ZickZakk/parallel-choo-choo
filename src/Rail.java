import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Georg on 23.06.2015.
 */
public class Rail
{
    public int Id;

    public List<Rail> connectedRails;

    public boolean isOccupied;

    public Lock ChooChooLock;

    public Rail(int id)
    {
        Id = id;
        connectedRails = new ArrayList<>();
        isOccupied = false;

        ChooChooLock = new ReentrantLock();
    }
}
