package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.DriverAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.events.TestFigure;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.LineFeature;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJobs2dPatterns {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     *
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        application.addTest("Figure Joe 1", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.FIGURE_JOE_1));
        application.addTest("Figure Joe 2", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.FIGURE_JOE_2));
        application.addTest("Figure Jane", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.FIGURE_JANE));
        application.addTest("Triangle", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.TRIANGLE));
        application.addTest("Square", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.SQUARE));
        application.addTest("Hexagon", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.HEXAGON));
        application.addTest("Cross", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.CROSS));
        application.addTest("Figure Joe 1 (ComplexCommand)", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.FIGURE_JOE_1_CC));
        application.addTest("Figure Joe 2 (ComplexCommand)", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), TestFigure.FIGURE_JOE_2_CC));
    }

    /**
     * Setup driver manager, and set default driver for application.
     *
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        Job2dDriver loggerDriver = new LoggerDriver();
        DriverFeature.addDriver("Logger Driver", loggerDriver);
        DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

        Job2dDriver testDriver = new DriverAdapter();
        DriverFeature.addDriver("Simulator", testDriver);

        Job2dDriver lineDriver = new DrawerAdapter();
        DriverFeature.addDriver("Simulator inc. lines", lineDriver);

        DriverFeature.updateDriverInfo();
    }

    private static void setupLines(Application application) {
        LineFeature.addLine("Line basic");
        LineFeature.addLine("Line dot");
        LineFeature.addLine("Line special");
    }

    /**
     * Auxiliary routines to enable using Buggy Simulator.
     *
     * @param application Application context.
     */
    private static void setupDefaultDrawerVisibilityManagement(Application application) {
        DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
        application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
                new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
        defaultDrawerWindow.setVisible(true);
    }

    /**
     * Setup menu for adjusting logging settings.
     *
     * @param application Application context.
     */
    private static void setupLogger(Application application) {
        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("2d jobs Visio");
                DrawerFeature.setupDrawerPlugin(app);
                //setupDefaultDrawerVisibilityManagement(app);

                DriverFeature.setupDriverPlugin(app);
                LineFeature.setupLinePlugin(app);
                setupDrivers(app);
                setupLines(app);
                setupPresetTests(app);
                setupLogger(app);

                app.setVisibility(true);
            }
        });
    }

}