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

        Overall:
        while (CurrentRail.Id != TargetRailId)
        {
            if (CurrentRail.Id > TargetRailId)
            {
                // Choo-Choo will abwärts.
                if (move((newRailId, currentRailId) -> newRailId < currentRailId, " Choo-Choo is arrived at "))
                {
                    continue Overall;
                }

                // Choo-Choo kann nicht abwärts, versucht aufwärts.
                if (move((newRailId, currentRailId) -> newRailId > currentRailId, " Choo-Choo went back to "))
                {
                    continue Overall;
                }
            }
            else
            {
                // Choo-Choo will aufwärts.
                if (move((newRailId, currentRailId) -> newRailId > currentRailId, " Choo-Choo is arrived at "))
                {
                    continue Overall;
                }

                // Choo-Choo kann nicht aufwärts, versucht abwärts.
                if (move((newRailId, currentRailId) -> newRailId < currentRailId, " Choo-Choo went back to "))
                {
                    continue Overall;
                }
            }
        }

        System.out.println(this.toString() + " Choo-Choo arrived at target station. Choo-Choo stored in Choo-Choo-shed.");
        CurrentRail.ChooChooLock.unlock();
    }

    private boolean move(Function<Integer, Boolean> railCheck, String stringToPrint)
    {
        for (Rail rail : CurrentRail.connectedRails)
        {
            if (!railCheck.apply(rail.Id, CurrentRail.Id))
            {
                continue;
            }

            if (rail.ChooChooLock.tryLock())
            {
                CurrentRail.ChooChooLock.unlock();
                CurrentRail = rail;

                System.out.println(this.toString() + stringToPrint + CurrentRail.Id);
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    private interface Function<A, R>
    {
        R apply(A one, A two);
    }
}
