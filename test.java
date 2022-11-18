import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class test implements ActionListener {
    private String pregunt, incisoA, incisoB, incisoC, incisoD, respuesta;
    private JTextArea mensaje;
    private int vidaBossInicial = 5;
    private JPanel Vida;
    private JButton start;
    private JLabel DQ;
    private JButton play;
    private JButton exit;
    private JTextArea pregunta;
    private File f;
    private int vidaPlayer = 10;
    private int vidaPlayerInicial = 10;
    private int vidaBoss = 5;
    private int i = 0;
    String paquete="Preguntas1";
    JFrame frame = new JFrame();
    JPanel VidaJ;
    JButton select;
    JRadioButton a;
    JRadioButton b;
    JRadioButton c;
    JRadioButton d;
    JButton continuar;
    JLabel imagen;
    JLabel noSoyFuelte;
    JButton Preguntas1;
    JButton Preguntas2;
    JButton Preguntas3;
    ImageIcon png;
    ImageIcon GameOver = new ImageIcon(getClass().getResource("GameOver.jpg"));
    ImageIcon boss2=new ImageIcon(getClass().getResource("SegundoFondo.png"));
    ImageIcon boss3=new ImageIcon(getClass().getResource("ChayanneBase.png"));
    ImageIcon boss4=new ImageIcon(getClass().getResource("ChayanneF2.png"));
    ImageIcon boss5=new ImageIcon(getClass().getResource("ChayanneMuerte.png"));
    private String nombreImagen = "PrimerFondo.png";
    Clip clip = AudioSystem.getClip();
    File question = new File("C:/DungeonsAndQuestions/");
    BufferedReader br;



    test() throws LineUnavailableException, UnsupportedAudioFileException, IOException { //Frame principal del Lore
        if(!question.exists())question.mkdirs();
        cancionLore();
    }
    public void GUI() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.add(lore());
        frame.add(sG());
        frame.setVisible(true);
    }

    public void backGround() {
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(31,32,34));
        frame.setLayout(null);
    }

    public Component lore() {
        mensaje = new JTextArea("Eres un estudiante de ISI en la Universidad de Sonora, un día eres teletransportado a una mazmorra peligrosa en un mundo desconocido, debes completar una serie de preguntas para  lograr volver a tu mundo sano y salvo... ¿Podrás escapar de aquí?");
        mensaje.setBounds(50, 50, 700, 150);
        mensaje.setBackground(Color.BLACK);
        mensaje.setForeground(Color.white);
        mensaje.setEditable(false);
        mensaje.setLineWrap(true);
        mensaje.setWrapStyleWord(true);
        mensaje.setFont(new Font("Courier new", Font.PLAIN, 22));
        return mensaje;
    }

    public Component sG() {
        start = new JButton("START");//Botton de start
        start.setBounds(300, 300, 200, 80);
        start.setFont((new Font("Hoefler Text", Font.PLAIN, 26)));
        start.addActionListener(e -> {
            mensaje.setVisible(false);
            backGround();
            start.setVisible(false);
            DQ.setVisible(true);
            play.setVisible(true);
            exit.setVisible(true);
        });

        ImageIcon DqIcon= new ImageIcon(getClass().getResource("DnQ.png"));
        DQ = new JLabel();
        DQ.setIcon(DqIcon);
        DQ.setBounds(250, 50, 450, 152);
        DQ.setVisible(false);
        frame.add(DQ);

        play = new JButton("PLAY");
        play.setBounds(375, 250, 200, 50);
        play.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
        play.addActionListener(e -> {
            backGround();
            exit.setVisible(false);
            play.setVisible(false);
            DQ.setVisible(false);
            displayPreguntas();
            cambiarImagen();
            vida();
            vidaPlayer();
            select.setVisible(false);
        });
        play.addActionListener(e -> {
            try {
                try{
                    siguientePregunta();
                }catch (NullPointerException E){
                    try {
                        question = new File("C:/DungeonsAndQuestions/" + paquete + ".txt");
                        br = new BufferedReader(new FileReader(question));
                        br.mark(100000);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    siguientePregunta();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        play.addActionListener(e -> {
            try {
                clip.close();
                primerCancion();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        play.setFocusable(false);
        frame.add(play);
        play.setVisible(false);

        select= new JButton("SELECT");
        select.setBounds(375, 325, 200, 50);
        select.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
        select.setFocusable(false);
        frame.add(select);
        select.setVisible(false);
        select.addActionListener(e -> {
            backGround();
            exit.setVisible(false);
            play.setVisible(false);
            select.setVisible(false);

            Preguntas1= new JButton("PAQUETE 1");
            Preguntas1.setBounds(75,225,200,100);
            Preguntas1.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas1.addActionListener(E ->{
                backGround();
                exit.setVisible(true);
                play.setVisible(true);
                select.setVisible(true);
                Preguntas1.setVisible(false);
                Preguntas2.setVisible(false);
                Preguntas3.setVisible(false);
                paquete = "Preguntas1";
                try {
                    question = new File("C:/DungeonsAndQuestions/" + paquete + ".txt");
                    br = new BufferedReader(new FileReader(question));
                    br.mark(100000);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Preguntas2= new JButton("PAQUETE 2");
            Preguntas2.setBounds(325,225,200,100);
            Preguntas2.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas2.addActionListener(E ->{
                backGround();
                exit.setVisible(true);
                play.setVisible(true);
                select.setVisible(true);
                Preguntas1.setVisible(false);
                Preguntas2.setVisible(false);
                Preguntas3.setVisible(false);
                paquete = "Preguntas2";
                try {
                    question = new File("C:/DungeonsAndQuestions/" + paquete + ".txt");
                    br = new BufferedReader(new FileReader(question));
                    br.mark(100000);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Preguntas3= new JButton("PAQUETE 3");
            Preguntas3.setBounds(575,225,200,100);
            Preguntas3.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas3.addActionListener(E ->{
                backGround();
                exit.setVisible(true);
                play.setVisible(true);
                select.setVisible(true);
                Preguntas1.setVisible(false);
                Preguntas2.setVisible(false);
                Preguntas3.setVisible(false);
                paquete = "Preguntas3";
                try {
                    question = new File("C:/DungeonsAndQuestions/" + paquete + ".txt");
                    br = new BufferedReader(new FileReader(question));
                    br.mark(100000);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            frame.add(Preguntas1);
            frame.add(Preguntas2);
            frame.add(Preguntas3);
        });
        start.addActionListener(e ->select.setVisible(true));
        exit = new JButton("EXIT");
        exit.setBounds(375, 400, 200, 50);
        exit.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
        exit.setFocusable(false);
        frame.add(exit);
        exit.setVisible(false);
        exit.addActionListener(e -> System.exit(0));


        return start;

    }

    public Component txtAreaPreguntas(int i) {
        pregunta = new JTextArea(añadirFacil(i));//if que pregunta estas (imagen, sonido, etc) retornaba arreglo en punto i
        pregunta.setBounds(650, 110, 300, 390);
        pregunta.setEditable(false);
        pregunta.setLineWrap(true);
        pregunta.setWrapStyleWord(true);
        pregunta.setFont(new Font("Courier new", Font.PLAIN, 16));
        pregunta.setVisible(true);
        frame.add(pregunta);
        return pregunta;
    }

    public void displayPreguntas() {
        txtAreaPreguntas(i);
        a = new JRadioButton("A.");
        a.setBounds(650, 510, 45, 20);
        a.setFont(new Font("Arial", Font.PLAIN, 18));
        a.setForeground(Color.BLACK);
        a.setFocusable(false);
        a.addActionListener(this);
        frame.add(a);
        b = new JRadioButton("B.");
        b.setBounds(732, 510, 45, 20);
        b.setFont(new Font("Arial", Font.PLAIN, 18));
        b.setForeground(Color.BLACK);
        b.setFocusable(false);
        b.addActionListener(this);
        frame.add(b);
        c = new JRadioButton("C.");
        c.setBounds(817, 510, 45, 20);
        c.setFont(new Font("Arial", Font.PLAIN, 18));
        c.setForeground(Color.BLACK);
        c.setFocusable(false);
        c.addActionListener(this);
        frame.add(c);
        d = new JRadioButton("D.");
        d.setBounds(890, 510, 45, 20);
        d.setFont(new Font("Arial", Font.PLAIN, 18));
        d.setForeground(Color.BLACK);
        d.setFocusable(false);
        d.addActionListener(this);
        frame.add(d);
        ButtonGroup agrupar = new ButtonGroup();
        agrupar.add(a);
        agrupar.add(b);
        agrupar.add(c);
        agrupar.add(d);
        frame.setVisible(true);
    }
    public void siguientePregunta() throws IOException {
        pregunt = br.readLine();
        incisoA = br.readLine();
        incisoB = br.readLine();
        incisoC = br.readLine();
        incisoD = br.readLine();
        respuesta = br.readLine();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == a) {
            if ((respuesta.compareTo("a")) == 0) {
                i++;
                try {
                    siguientePregunta();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pregunta.setText(añadirFacil(i));
                vidaBoss--;
                Vida.setBounds(15, 515, ((vidaBoss * 615) / vidaBossInicial), 60);
            } else {
                vidaPlayer--;
                VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);
                if (vidaPlayer == 0) {
                    cerrar();
                    try {
                        br.reset();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.add(noSoyFuelte);
                    mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                    mensaje.setBounds(50, 50, 700, 75);
                }
            }
        }
        System.out.println("Vida del jugador: " + vidaPlayer + "\nVida del boss: " + vidaBoss);

        if (e.getSource() == b) {
            if ((respuesta.compareTo("b")) == 0) {
                i++;
                try {
                    siguientePregunta();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pregunta.setText(añadirFacil(i));
                vidaBoss--;
                Vida.setBounds(15, 515, ((vidaBoss * 615) / vidaBossInicial), 60);
            } else {
                vidaPlayer--;
                VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);
                if (vidaPlayer == 0) {
                    cerrar();
                    try {
                        br.reset();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.add(noSoyFuelte);
                    mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                    mensaje.setBounds(50, 50, 700, 75);
                }
            }
            System.out.println("Vida del jugador: " + vidaPlayer + "\nVida del boss: " + vidaBoss);
        }
        if (e.getSource() == c) {
            if ((respuesta.compareTo("c")) == 0) {
                i++;
                try {
                    siguientePregunta();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pregunta.setText(añadirFacil(i));
                vidaBoss--;
                Vida.setBounds(15, 515, ((vidaBoss * 615) / vidaBossInicial), 60);
            } else {
                vidaPlayer--;
                VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);
                if (vidaPlayer == 0) {
                    cerrar();
                    try {
                        br.reset();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.add(noSoyFuelte);
                    mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                    mensaje.setBounds(50, 50, 700, 75);
                }
            }
        }

        if (e.getSource() == d) {
            if ((respuesta.compareTo("d")) == 0) {
                i++;
                try {
                    siguientePregunta();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pregunta.setText(añadirFacil(i));
                vidaBoss--;
                Vida.setBounds(15, 515, ((vidaBoss * 615) / vidaBossInicial), 60);
            } else {
                vidaPlayer--;
                VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);
                if (vidaPlayer == 0) {
                    cerrar();
                    try {
                        br.reset();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.add(noSoyFuelte);
                    mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                    mensaje.setBounds(50, 50, 700, 75);
                }
            }
            System.out.println("Vida del jugador: " + vidaPlayer + "\nVida del boss: " + vidaBoss);
            if (i == 30) {
                Vida.setVisible(false);
                VidaJ.setVisible(false);
                cerrar();
                try {
                    br.reset();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }

    public String añadirFacil(int i) {
        if (i == 5) {
            clip.stop();
            GUI();
            start.setVisible(false);
            mensaje.setText("Vaya haz logrado vencer al primero, así es, el primero de varios ¿esperabas solamente a uno? ingenuo, aun te queda un camino por recorrer suerte...");
            continuar = new JButton("CONTINUE");
            continuar.setBounds(300, 300, 200, 80);
            frame.add(continuar);
            pregunta.setVisible(false);
            a.setVisible(false);
            b.setVisible(false);
            c.setVisible(false);
            d.setVisible(false);
            imagen.setVisible(false);
            Vida.setVisible(false);
            VidaJ.setVisible(false);
            continuar.addActionListener(e -> {
                pregunta.setVisible(true);
                backGround();
                mensaje.setVisible(false);
                a.setVisible(true);
                b.setVisible(true);
                c.setVisible(true);
                d.setVisible(true);
                continuar.setVisible(false);
                imagen.setIcon(boss2);
                imagen.setVisible(true);
                Vida.setVisible(true);
                VidaJ.setVisible(true);
            });
            continuar.addActionListener(e -> {
                try {
                    segundaCancion();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }

            });
            vidaBoss = 11;
            vidaBossInicial = 10;
            Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60);
        }
        if (i == 15) {
            clip.stop();
            GUI();
            start.setVisible(false);
            mensaje.setText("Tras una ardua pelea percibes una melodía detrás de la puerta, tu cuerpo siente escalofríos y tu respiración se agita por el aura que emana del cuarto, al entrar te encuentras con la encarnación del mismisimo diablo");
            continuar = new JButton("CONTINUE");
            continuar.setBounds(300, 300, 200, 80);
            frame.add(continuar);
            pregunta.setVisible(false);
            a.setVisible(false);
            b.setVisible(false);
            c.setVisible(false);
            d.setVisible(false);
            imagen.setVisible(false);
            Vida.setVisible(false);
            VidaJ.setVisible(false);
            continuar.addActionListener(e -> {
                backGround();
                mensaje.setVisible(false);
                a.setVisible(true);
                b.setVisible(true);
                c.setVisible(true);
                d.setVisible(true);
                continuar.setVisible(false);
                imagen.setIcon(boss3);
                imagen.setVisible(true);
                Vida.setVisible(true);
                VidaJ.setVisible(true);
                pregunta.setVisible(true);
            });

            continuar.addActionListener(e -> {
                try {
                    terceraCancion();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            });
            vidaBossInicial = 15;
            vidaBoss = 16;
            Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60);
        }
        if(i ==20){
            imagen.setIcon(boss4);
        }
        if(i ==25) {
            imagen.setIcon(boss5);
        }
        return (pregunt + "\n" + incisoA + "\n" + incisoB + "\n" + incisoC + "\n" + incisoD);

    }
    public Component cambiarImagen(){
        imagen=new JLabel();
        imagen.setBounds(0,0,650,500);
        png=new ImageIcon(getClass().getResource(nombreImagen));
        imagen.setIcon(png);
        frame.add(imagen);
        imagen.setVisible(true);
        return imagen;
    }

    public void cerrar(){
        GUI();
        clip.stop();
        start.setVisible(false);
        mensaje.setText("Haz logrado derrotar al mismisimo diablo, con ello, reuniste la magia suficiente para teletransportarte a tu mundo, despiertas en el baño del edificio 5J y todo fue sueño... resultó que todo fue por culpa de unos papa boneless podridos que tenían hongos alucinógenos");
        JButton playAgain= new JButton("PLAY AGAIN");
        playAgain.setBounds(300,315,200,80);
        noSoyFuelte = new JLabel();
        noSoyFuelte.setBounds(250,50,400,300);
        noSoyFuelte.setIcon(GameOver);
        continuar = new JButton("EXIT");
        continuar.setBounds(300, 425, 200, 80);
        frame.add(continuar);
        frame.add(playAgain);
        pregunta.setVisible(false);
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        imagen.setVisible(false);
        Vida.setVisible(false);
        VidaJ.setVisible(false);
        playAgain.addActionListener(e ->{
          frame.dispose();
          frame=new JFrame();
          GUI();
          vidaPlayer = 10;
          vidaBoss = 5;
          vidaBossInicial=5;
          i = 0;
        });
        continuar.addActionListener(e -> System.exit(0));
    }
    void cancionLore() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        f = new File("GOTOpening.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
        clip.open(audioStream);
        clip.start();
    }
    void primerCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        f = new File("Gwyn.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
        clip.open(audioStream);
        clip.start();
    }
    void segundaCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        f = new File("AbyssWatchers.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    void terceraCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        f = new File("Vordt.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    public void vida(){
        Vida=new JPanel();
        Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60);
        Vida.setBackground(Color.RED);
        Vida.setVisible(true);
        frame.add(Vida);
    }
    public void vidaPlayer(){
        VidaJ=new JPanel();
        VidaJ.setBounds(660,20,((vidaPlayer*270)/vidaPlayerInicial),60);
        VidaJ.setBackground(Color.RED);
        VidaJ.setVisible(true);
        frame.add(VidaJ);
    }


}
