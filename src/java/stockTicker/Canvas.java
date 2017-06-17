package stockTicker;

import java.util.logging.*;

final class Canvas extends Thread {

    static final long INTERVAL = 10;
    String value = "begining";
   // String value;

    /**
     * Periodically updates stock info and notifies servlet threads.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (this) { 
                Logger.getGlobal().log(Level.INFO, this.toString());
                notifyAll();
            }
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                return;
            }
       }
    }
    
    @Override
    public String toString(){
          return  value;          
    }
    
    public void updatePlayers(){
        value = "{ \"PLAYERS\": [ [\"P1\", 8, 0, 0] , [\"P2\", 5, 44, 0] , [\"P3\", -6, 0, 44] , [\"P4\", 10, 44, 44]]}" ;
    }
    
}
