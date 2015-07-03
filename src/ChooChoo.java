import java.time.ZonedDateTime;
import java.util.function.Predicate;

/**
 * Created by Georg on 23.06.2015.
 */
public class ChooChoo
{
    public int TargetRailId;

    public Rail CurrentRail;

    public ChooChoo(int targetRailId)
    {
        TargetRailId = targetRailId;
    }

    public void start(Rail startingRail)
    {
        startingRail.ChooChooLock.lock();
        CurrentRail = startingRail;

        while (CurrentRail.Id != TargetRailId)
        {
            if (CurrentRail.Id > TargetRailId)
            {
                // Choo-Choo will abwärts.
                if (move((newRail) -> newRail.Id < CurrentRail.Id, " Choo-Choo is arrived at "))
                {
                    continue;
                }

                // Choo-Choo kann nicht abwärts, versucht aufwärts.
                if (move((newRail) -> newRail.Id > CurrentRail.Id, " Choo-Choo went back to "))
                {
                    try
                    {
                        // Choo-Choo wartet kurz, weil es Platz gemacht hat.
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                // Choo-Choo will aufwärts.
                if (move((newRail) -> newRail.Id > CurrentRail.Id, " Choo-Choo is arrived at "))
                {
                    continue;
                }

                // Choo-Choo kann nicht aufwärts, versucht abwärts.
                if (move((newRail) -> newRail.Id < CurrentRail.Id, " Choo-Choo went back to "))
                {
                    try
                    {
                        // Choo-Choo wartet kurz, weil es Platz gemacht hat.
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println(ZonedDateTime.now().toLocalTime() + " " + this.toString() + " Choo-Choo arrived at target station. Choo-Choo stored in Choo-Choo-shed.");
        CurrentRail.ChooChooLock.unlock();
    }

    private boolean move(Predicate<Rail> railCheck, String stringToPrint)
    {
        for (Rail rail : CurrentRail.connectedRails)
        {
            if (!railCheck.test(rail))
            {
                continue;
            }

            if (rail.ChooChooLock.tryLock())
            {
                CurrentRail.ChooChooLock.unlock();
                CurrentRail = rail;

                System.out.println(ZonedDateTime.now().toLocalTime() + " " + this.toString() + stringToPrint + (CurrentRail.Id + 1));

                return true;
            }
        }

        return false;
    }
}
