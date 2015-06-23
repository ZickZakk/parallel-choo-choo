import java.util.ArrayList;

/**
 * Created by Georg on 23.06.2015.
 */
public class Program
{
    public static void main(String[] args)
    {
        ArrayList<Rail> rails = new ArrayList<>();

        for (int i = 0; i < 6; i++)
        {
            rails.add(new Rail(i));
        }

        initilialize(rails);

        ChooChoo chooChooTo5 = new ChooChoo(5);
        ChooChoo chooChooTo0 = new ChooChoo(0);

        new Thread(() -> chooChooTo5.start(rails.get(0))).start();
        new Thread(() -> chooChooTo0.start(rails.get(5))).start();
    }

    private static void initilialize(ArrayList<Rail> rails)
    {
        rails.get(0).connectedRails.add(rails.get(1));

        rails.get(1).connectedRails.add(rails.get(2));
        rails.get(1).connectedRails.add(rails.get(3));
        rails.get(1).connectedRails.add(rails.get(0));

        rails.get(2).connectedRails.add(rails.get(1));
        rails.get(2).connectedRails.add(rails.get(4));

        rails.get(3).connectedRails.add(rails.get(1));
        rails.get(3).connectedRails.add(rails.get(4));

        rails.get(4).connectedRails.add(rails.get(3));
        rails.get(4).connectedRails.add(rails.get(2));
        rails.get(4).connectedRails.add(rails.get(5));

        rails.get(5).connectedRails.add(rails.get(4));
    }
}
