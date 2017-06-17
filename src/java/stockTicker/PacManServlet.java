package stockTicker;

import java.io.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


@WebServlet(urlPatterns = {"/game"})
public final class PacManServlet extends HttpServlet {

    final Canvas canvas = new Canvas();

    @Override
    public void init(final ServletConfig config) {
        canvas.start();
        Logger.getGlobal().log(Level.INFO, "Started Updates");
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream,charset=UTF-8");

        try (final PrintWriter out = response.getWriter()) {

            //while (!Thread.interrupted()) {
                synchronized (canvas) {
                    canvas.wait();
                }
                out.print("data: ");
                out.println(canvas);
                out.println();
                out.flush();
            //}
        } catch (InterruptedException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/event-stream,charset=UTF-8");

        canvas.updatePlayers();
       // canvas.start();
        
    }

    @Override
    public void destroy() {
        try {
            canvas.interrupt();
            canvas.join();
            Logger.getGlobal().log(Level.INFO, "Stopped Updates");

        } catch (InterruptedException e) {
        }
    }
}
