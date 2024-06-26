package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class Client_GUI extends JFrame {
    private final JPanel jPanel;
    private final JMenuItem execute;
    private final JMenuItem clear;
    private final JMenuItem refresh;
    private final JMenuItem info;
    private final JMenuItem queryD;
    private final JTextArea querry, output;
    private final JMenuBar jMenuBar;
    private final JMenuItem exit;
    private final FileExplorer fileExplorer;
    private String currentDBname;
    private JCheckBox[] checkBoxes;
    private QueryDesignerFrame queryDesignerFrame;
    private SelectorFrame selectorFrame;
    private InfoFrame infoFrame;
    private Client client;

    private boolean toResetTA = true;
    public Client_GUI(Client client) {
        jPanel = new JPanel();

        this.setLayout(new BorderLayout());

        if(System.getProperty("os.name").equalsIgnoreCase("linux")){
            this.setPreferredSize(new Dimension(1600, 850));
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        int padding = 15;
        Insets insets = new Insets(padding,padding,padding,padding);

        ImageIcon exiticon = new ImageIcon("src/main/resources/x.png");

        Image ximage = exiticon.getImage();
        Image xnewimg = ximage.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        exiticon = new ImageIcon(xnewimg);

        exit = new JMenuItem("   EXIT",exiticon);
        exit.setPreferredSize(new Dimension(60,40));
        exit.setBackground(new Color(75, 104, 178));

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exit.setBackground(new Color(75, 104, 178));
            }
        });

        ImageIcon execicon = new ImageIcon("src/main/resources/garrow.png");

        Image image = execicon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        execicon = new ImageIcon(newimg);


        execute = new JMenuItem("     EXECUTE",execicon);
        execute.setBackground(new Color(75, 104, 178));

        execute.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                execute.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                execute.setBackground(new Color(75, 104, 178));
            }
        });

        ImageIcon clicon = new ImageIcon("src/main/resources/eraser.png");

        Image climage = clicon.getImage();
        Image clnewimg = climage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        clicon = new ImageIcon(clnewimg);

        clear = new JMenuItem("   CLEAR",clicon);
        clear.setBackground(new Color(75, 104, 178));
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clear.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                clear.setBackground(new Color(75, 104, 178));
            }
        });

        ImageIcon ricon = new ImageIcon("src/main/resources/refric.png");

        Image rimage = ricon.getImage();
        Image rnewimg = rimage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        ricon = new ImageIcon(rnewimg);
        refresh = new JMenuItem("   REFRESH",ricon);
        refresh.setBackground(new Color(75, 104, 178));
        refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refresh.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                refresh.setBackground(new Color(75, 104, 178));
            }
        });

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileExplorer.refreshTree();
            }
        });

        ImageIcon iicon = new ImageIcon("src/main/resources/infoic.png");

        Image iimage = iicon.getImage();
        Image inewimg = iimage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        iicon = new ImageIcon(inewimg);

        info = new JMenuItem("INFO",iicon);
        info.setBackground(new Color(75, 104, 178));
        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                info.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                info.setBackground(new Color(75, 104, 178));
            }
        });


        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoFrame = new InfoFrame();
            }
        });

        ImageIcon qdicon = new ImageIcon("src/main/resources/qdic.png");

        Image qdimage = qdicon.getImage();
        Image qdnewimg = qdimage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        qdicon = new ImageIcon(qdnewimg);

        queryD = new JMenuItem("QUERY DESIGNER",qdicon);
        queryD.setBackground(new Color(75, 104, 178));
        queryD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                queryD.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                queryD.setBackground(new Color(75, 104, 178));
            }
        });

        queryD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currendDBname =  client.getCurrentDBname();
                selectorFrame = new SelectorFrame(currendDBname);
                checkBoxes = SelectorFrame.getjCheckBoxes();


            }
        });

        jMenuBar = new JMenuBar();

        jMenuBar.add(refresh);
        jMenuBar.add(clear);
        jMenuBar.add(queryD);
        jMenuBar.add(execute);
        jMenuBar.add(info);
        jMenuBar.add(exit);



        exit.addActionListener(_ -> dispose());

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                querry.setText("");
            }
        });

        //////////////////////////


        //this.add(fileExplorer);


        querry = new JTextArea();
        querry.setFont(new Font("Cfont", Font.ITALIC, 20));

        querry.setText("Write your command(s) here");

        querry.setBorder(new EmptyBorder(insets));

        querry.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(toResetTA) {
                    querry.setText("");
                    querry.setFont(new Font("Cfont", Font.PLAIN, 20));
                    toResetTA = false;
                }
            }
        });

        JScrollPane qScrollPane = new JScrollPane(querry);
        qScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollPane.setPreferredSize(new Dimension(1400,440));
        jPanel.add(qScrollPane);


        output = new JTextArea();
        output.setFont(new Font("Cfont", Font.PLAIN, 20));

        output.setBorder(new EmptyBorder(insets));

        querry.setBackground(new Color(239, 240, 243));

        output.setBackground(new Color(239, 240, 243));

        output.setEditable(false);

        fileExplorer = new FileExplorer(client, output);
        fileExplorer.setPreferredSize(new Dimension(175,3000));


        client.setOutputTextArea(output);

        JScrollPane oScrollPane = new JScrollPane(output);
        oScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        oScrollPane.setPreferredSize(new Dimension(1400,440));
        jPanel.add(oScrollPane);

        //////////////////////////////////////


        this.add(fileExplorer,BorderLayout.WEST);

        this.add(jMenuBar, BorderLayout.NORTH);
        this.add(jPanel, BorderLayout.CENTER);

        jPanel.setBackground(new Color(75, 104, 178));

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/dbicon.png");
        setIconImage(icon);

        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JMenuItem getExecuteButton() {
        return execute;
    }

    public String getjTextField() {
        return querry.getText();
    }

    public JMenuItem getExitButton() {
        return exit;
    }

    public JTextArea getOutputTextArea() {
        return output;
    }
}