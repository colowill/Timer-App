package clockapplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author 2headaxe || William Frederick Minor
 * email: willminor64@gmail.com
 * Linkedin: https://www.linkedin.com/in/william-minor-98656a251/
 * 
 */


/**
 * A Clock class that creates a stopwatch/alarm clock application using GUI
 * Features options to start/stop/reset time, add hours, minutes, and seconds, and switch different alarm sounds
 */
public class Clock implements ActionListener {

    // Mane components of the GUI
    JFrame frame0 = new JFrame();
    JLabel timeLabel1 = new JLabel();
    JLabel image = new JLabel();
    
    // Sound files for alarm options and their names
    String[] soundFiles = {"alarm_beep.wav","air_raid.wav","airplane_chime.wav","cuckoo_clock.wav"};
    String[] alarmSounds = {"Alarm", "Siren", "Chime", "Clock"};
    JComboBox soundMenu = new JComboBox(alarmSounds);
    
    // Buttons for start, reset, and toggle bewteen the stopwatch and alarm modes
    JButton startBtn = new JButton("START");
    JButton resetBtn = new JButton("RESET");
    JButton toggleBtn = new JButton();
    
    // Buttons to add/remove seconds, hours, and minutes in alarm mode
    JButton hrAdd = new JButton("+");
    JButton hrSub = new JButton("-");
    JButton minAdd = new JButton("+");
    JButton minSub = new JButton("-");
    JButton secAdd = new JButton("+");
    JButton secSub = new JButton("-");
    JButton[] btnArray = {hrAdd,minAdd,secAdd,hrSub,minSub,secSub};
    
    // Variables that calculate time passed using miliseconds and system time
    int timePassed = 0;
    int hours = (timePassed/3600000);
    int minutes = (timePassed/60000) % 60;
    int seconds = (timePassed/1000) % 60;
    int milSeconds = (timePassed/100) % 10;
    static int h = 3600000;
    static int m = 60000;
    static int s = 1000;
    String msString = String.format("%01d", milSeconds);
    String secString = String.format("%02d", seconds);
    String minString = String.format("%02d", minutes);
    String hrString = String.format("%02d", hours);
    
    // Keeps track of which mode is currently on
    boolean started = false;
    boolean timerMode = false;
    
    // Instance of the Sound class for alarm sounds
    Sound sound = new Sound();
    
    
    /*
    Timer method that performs a specified action every 100 miliseconds. Every 100 miliseconds adds 100 to the varible
    timePassed, which basically acts as a stopwatch. It then calculates each passing second, minute, and hour, and updates
    the String variables and timeLabel1 JFrame. In alarm mode, when time = 0 an alarm sound is played.
    */
    Timer timer = new Timer(100, new ActionListener() {
    
        public void actionPerformed(ActionEvent e) {
            
        if (timePassed>=0) {
            if (!timerMode) {
                timePassed+=100;
            }
            else {
                timePassed-=100;
                if (timePassed==0 && timerMode) {
                    sound.playSound(soundFiles[soundMenu.getSelectedIndex()],true);
                }
            }
        if (timePassed<0) {
            timePassed=0;
        }
            updateTime();
        }
        
        }
    });
    
    /**
     * Constructor to set the style of the interface of the application
     */
    Clock() {
        
        // Time display
        timeLabel1.setText(hrString+":"+minString+":"+secString+"."+msString);
        timeLabel1.setBounds(55,80,250,80);
        timeLabel1.setFont(new Font("Verdana",Font.PLAIN,32));
        timeLabel1.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel1.setOpaque(true);
        timeLabel1.setHorizontalAlignment(JTextField.CENTER);
        
        // Start button setup
        startBtn.setBounds(60,205,100,50);
        startBtn.setFont(new Font("Verdana",Font.PLAIN,18));
        startBtn.setFocusable(false);
        startBtn.addActionListener(this);
        
        // Reset button setup
        resetBtn.setBounds(200,205,100,50);
        resetBtn.setFont(new Font("Verdana",Font.PLAIN,18));
        resetBtn.setFocusable(false);
        resetBtn.addActionListener(this);
        
        // Toggle button setup
        toggleBtn.setBounds(310,85,30,70);
        toggleBtn.setOpaque(true);
        toggleBtn.setFocusable(false);
        toggleBtn.addActionListener(this);
        
        // Alarm sound drop-down menu setup
        soundMenu.setBounds(310,160,30,30);
        soundMenu.setOpaque(true);
        soundMenu.setFocusable(false);
        soundMenu.addActionListener(this);
        soundMenu.setVisible(false);
        
        // Buttons for incrementing/decrimenting time in alarm mode, only show up when in alarm mode
        minAdd.setBounds(105,25,150,50);
        minAdd.setVisible(true);
        
        int xBoundSub = 100;
        int xBoundAdd = xBoundSub;
        for (int i = 0; i < btnArray.length; i ++) {
            if (i<3) {
                btnArray[i].setBounds(xBoundAdd,55,20,20);
                btnArray[i].setFont(new Font("Verdana",Font.PLAIN,8));
                btnArray[i].setVisible(false);
                btnArray[i].addActionListener(this);
                frame0.add(btnArray[i]);
                xBoundAdd+=55;
            } else {
                btnArray[i].setBounds(xBoundSub,165,20,20);
                btnArray[i].setFont(new Font("Verdana",Font.PLAIN,8));
                btnArray[i].setVisible(false);
                btnArray[i].addActionListener(this);
                frame0.add(btnArray[i]);
                xBoundSub+=55;
            }
        }
        
        // Adding all the components to the frame
        frame0.add(timeLabel1);       
        frame0.add(startBtn);
        frame0.add(resetBtn);
        frame0.add(toggleBtn);
        frame0.add(soundMenu);
        frame0.setTitle("Stopwatch");
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setSize(360,360);
        frame0.setLayout(null);
        frame0.setResizable(false);
        frame0.setVisible(true);
    }
    
    // Function to start the timer
    void start() {
        timer.restart();
        started=true;
    }
    
    // Stops the timer and sets time back to 0 using the reset() method
    void stop() {
        timer.stop();
        started=false;
        reset();
    }
    
    // Resets the time back to 0
    void reset() {
        timer.stop();
        timePassed=0;
        updateTime();
        startBtn.setText("START");
        started=false;
    }
    
    // Updates the time in the timed display of the GUI, calculates using milliseconds
    void updateTime() {
        hours = (timePassed/3600000);
        minutes = (timePassed/60000) % 60;
        seconds = (timePassed/1000) % 60;
        milSeconds = (timePassed/100) % 10;
        msString = String.format("%01d", milSeconds);
        secString = String.format("%02d", seconds);
        minString = String.format("%02d", minutes);
        hrString = String.format("%02d", hours);
        timeLabel1.setText(hrString+":"+minString+":"+secString+"."+msString);
    }
    
    // Handles button events through the actionPerformed implimentation
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startBtn) {
            
            if (started==false) {
                started = true;
                startBtn.setText("STOP");
                start();
            }
            else {
                started = false;
                startBtn.setText("START");
                stop();
            }
            sound.stopSound();
            
        } if (e.getSource()==resetBtn) {
            started = false;
            startBtn.setText("START");
            reset();
            sound.stopSound();
            
        } if (e.getSource()==toggleBtn) {
            if (hrAdd.isVisible()== false) {
                reset();
                hrAdd.setVisible(true);
                hrSub.setVisible(true);
                minAdd.setVisible(true);
                minSub.setVisible(true);
                secAdd.setVisible(true);
                secSub.setVisible(true);
                soundMenu.setVisible(true);
                frame0.setTitle("Timer");
                timerMode=true;
              
            } else {
                reset();
                hrAdd.setVisible(false);
                hrSub.setVisible(false);
                minAdd.setVisible(false);
                minSub.setVisible(false);
                secAdd.setVisible(false);
                secSub.setVisible(false);
                soundMenu.setVisible(false);
                frame0.setTitle("Stopwatch");
                timerMode=false;
            }
            reset();
            sound.stopSound();
        }
        if (e.getSource()==hrAdd) {
            if (hours<=99) {
                timePassed+=h;
                updateTime();
            }
        } if (e.getSource()==minAdd) {
            if (minutes<=60) {
                timePassed+=m;
                updateTime();
            }
        } if (e.getSource()==secAdd) {
            if (seconds<=60) {
                timePassed+=s;
                updateTime();
            }
        }
        if (e.getSource()==hrSub) {
            if (hours>0) {
                timePassed-=h;
                updateTime();
            }
        } if (e.getSource()==minSub) {
            if (minutes>0) {
                timePassed-=m;
                updateTime();
            }
        } if (e.getSource()==secSub) {
            if (seconds>0) {
                timePassed-=s;
                updateTime();
            }
        } 
}
}
