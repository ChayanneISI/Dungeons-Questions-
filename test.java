import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//TODO Cuando se escribió este código solo dios y nosotros sabe lo que hace
//TODO Ahora solo dios lo sabe... Amén :c
public class test implements ActionListener {
    private String pregunt, incisoA, incisoB, incisoC, incisoD, respuesta; //Variables de preguntas
    private String paquete="Preguntas1"; //Paquete inicial de preguntas (1 por default)
    private String nombreImagen = "PrimerFondo.png"; // Nombre de la primera imagen a usar
    private int vidaBossInicial = 5; // Vida base del boss para regla de 3 con el rectangulo de vida
    private int vidaPlayer = 10; //Vida del player
    private int vidaPlayerInicial = 10; // Vida base del player para regla de 3 con el rectangulo de vida
    private int vidaBoss = 5; //Vida del primer boss
    private int i = 0; // Variable auxiliar para revisar en que pregunta vamos
    private JTextArea mensaje;//componente de textArea donde se muestra el lore al inicio
    private JPanel Vida; //Panel con rectangulo de vida
    private JButton start; //Boton start
    private JLabel DQ; //Label en donde
    private JButton play;//Boton para empezar a jugar
    private JButton exit;//Boton para cerrar el juego
    private JTextArea pregunta;//componente textArea para mostar las preguntas de los archivos
    private File f;//archivo para acceder a la música
    JFrame frame = new JFrame();//es el frame que contiene los componentes de nuestro juego
    JPanel VidaJ;//Panel que tiene el fin de mostrar la vida del jugador
    JButton select;//Botón para selecciones los paquetes de pregunta que se desean utilizar
    JRadioButton a;//radio boton para la opción A
    JRadioButton b;//radio boton para la opción B
    JRadioButton c;//radio boton para la opción C
    JRadioButton d;//radio boton para la opción D
    JButton continuar; //boton continuar que aparece cada que vences a un boss y al picarle permite avanzar al siguiete boss, mostrando su imagen y preguntas.
    JLabel imagen;//Label que contiene las imagenes de los boses
    JLabel noSoyFuelte;//label que contiene la imagen cuando eres derrotado por los bosses
    JButton Preguntas1;//Boton para definir el paquete de pregunta 1
    JButton Preguntas2;//Boton para definir el paquete de pregunta 2
    JButton Preguntas3;//Boton para definir el paquete de pregunta 3
    ImageIcon png;
    ImageIcon GameOver = new ImageIcon(getClass().getResource("GameOver.jpg"));
    ImageIcon boss2=new ImageIcon(getClass().getResource("SegundoFondo.png"));
    ImageIcon boss3=new ImageIcon(getClass().getResource("ChayanneBase.png"));
    ImageIcon boss4=new ImageIcon(getClass().getResource("ChayanneF2.png"));
    ImageIcon boss5=new ImageIcon(getClass().getResource("ChayanneMuerte.png"));
    Clip clip = AudioSystem.getClip();
    File question = new File("C:/DungeonsAndQuestions/");
    BufferedReader br;

    test() throws LineUnavailableException, UnsupportedAudioFileException, IOException { //TODO Frame principal del Lore
        if(!question.exists())question.mkdirs();
        cancionLore();
    }
    public void GUI() {
        //TODO Frame principal de los menús pequeños, contiene fondo y propiedades del frame
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
        //TODO Método para los frames grandes, incluye el fondo gris y propiedades basicas de la ventana
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(31,32,34));
        frame.setLayout(null);
    }

    public Component lore() {
        // TODO Componente que muestra el mensaje de lore en la ventana chica
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
        //Metodo que permite usar los botones de start, select y exit
        start = new JButton("START");//Botton de start
        start.setBounds(300, 300, 200, 80);
        start.setFont((new Font("Hoefler Text", Font.PLAIN, 26)));
        start.addActionListener(e -> { //TODO Listener del boton start
            //Oculta lo que tenga el frame y llama al background
            mensaje.setVisible(false);
            backGround();
            start.setVisible(false);
            DQ.setVisible(true);
            play.setVisible(true);
            exit.setVisible(true);
            select.setVisible(true);
        });

        ImageIcon DqIcon= new ImageIcon(getClass().getResource("DnQ.png")); //Imagen del logotipo del juego
        DQ = new JLabel();
        DQ.setIcon(DqIcon);
        DQ.setBounds(250, 50, 450, 152);
        DQ.setVisible(false);
        frame.add(DQ); //Añadimos la imagen

        play = new JButton("PLAY"); //Boton play
        play.setBounds(375, 250, 200, 50);
        play.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
        play.addActionListener(e -> { //TODO Listener del play
            //TODO Llamamos a background, ocultamos lo que tenia el frame
            //TODO llamamos a display preguntas para que muestre la pregunta numero i,
            //TODO Llamamos a cambiar imagen para que coloque la primera imagen
            //TODO llamamos a vida y vidaPlayer para que muestre la cantidad de vida del player y boss
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
        play.addActionListener(e -> { //TODO Listener del play
            try {
                try{
                    siguientePregunta();//Lee archivo y si no se define uno nos va dar el NullPointerException,
                }catch (NullPointerException E){//despues nos intenta definir el paquete que tenemos por default con el siguiente try
                    try {
                        question = new File("C:/DungeonsAndQuestions/" + paquete + ".txt");
                        br = new BufferedReader(new FileReader(question));
                        br.mark(100000);//TODO ponemos un checkpoint a la lectura
                        //TODO en el carácter 0, para poder volver a la pregunta 1 en caso de fallar o volver a jugar
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    siguientePregunta();//Se vuelve a llamar el método porque aquí ya tiene definido un paquete y no nos va dar una excepción NullPointer.
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        play.addActionListener(e -> {
            try { //TODO Quitamos la cancion de Game of Thrones y ponemos la del primer boss
                clip.close();
                primerCancion();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });
        play.setFocusable(false);
        frame.add(play);
        play.setVisible(false);

        select= new JButton("SELECT"); //TODO boton del select para el paquete de preguntas
        select.setBounds(375, 325, 200, 50);
        select.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
        select.setFocusable(false);
        frame.add(select);
        select.setVisible(false);
        select.addActionListener(e -> {//TODO listener del select
            backGround();
            exit.setVisible(false);
            play.setVisible(false);
            select.setVisible(false);

            Preguntas1= new JButton("PAQUETE 1"); //TODO botón del select
            Preguntas1.setBounds(115,265,200,100);
            Preguntas1.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas1.addActionListener(E ->{ //TODO Listener del select, oculta todo y muestra el exit,play y select,
                //TODO tambien asigna el archivo a abrir según el botón que elija el usuario
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
                    br.mark(100000);//TODO ponemos un checkpoint a la lectura
                    //TODO en el caracter 0, para poder volver a la pregunta 1 en caso de fallar o volver a jugar
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Preguntas2= new JButton("PAQUETE 2");
            Preguntas2.setBounds(365,265,200,100);
            Preguntas2.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas2.addActionListener(E ->{//TODO Listener del paquete2, oculta todo y muestra el exit,play y select,
                //TODO tambien asigna el archivo a abrir según el botón que elija el usuario
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
                    br.mark(100000);//TODO ponemos un checkpoint a la lectura
                    //TODO en el caracter 0, para poder volver a la pregunta 1 en caso de fallar o volver a jugar
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Preguntas3= new JButton("PAQUETE 3");
            Preguntas3.setBounds(615,265,200,100);
            Preguntas3.setFont(new Font("Hoefler Text", Font.PLAIN, 26));
            Preguntas3.addActionListener(E ->{//TODO Listener del paquete3, oculta todo y muestra el exit,play y select,
                //TODO tambien asigna el archivo a abrir según el botón que elija el usuario
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
                    br.mark(100000);//TODO ponemos un checkpoint a la lectura
                    //TODO en el caracter 0, para poder volver a la pregunta 1 en caso de fallar o volver a jugar
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            //TODO añade los paquetes de preguntas al frame (botones)
            frame.add(Preguntas1);
            frame.add(Preguntas2);
            frame.add(Preguntas3);
        });
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
        pregunta.setBounds(645, 110, 300, 390);
        pregunta.setEditable(false);
        pregunta.setLineWrap(true);
        pregunta.setWrapStyleWord(true);
        pregunta.setFont(new Font("Courier new", Font.PLAIN, 16));
        pregunta.setVisible(true);
        frame.add(pregunta);
        return pregunta;
    }

    public void displayPreguntas() { //TODO Metodo que inserta la pregunta numero i, ademas de los incisos a b c d
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
        ButtonGroup agrupar = new ButtonGroup(); //TODO funcion para hacer que solo un boton sea seleccionable
        agrupar.add(a);
        agrupar.add(b);
        agrupar.add(c);
        agrupar.add(d);
        frame.setVisible(true);
    }
    public void siguientePregunta() throws IOException { //TODO método para leer el archivo.txt para saber:
        //TODO Pregunta, incisos a, b, c, d, y la respuesta
        pregunt = br.readLine();
        incisoA = br.readLine();
        incisoB = br.readLine();
        incisoC = br.readLine();
        incisoD = br.readLine();
        respuesta = br.readLine();
    }

    @Override
    public void actionPerformed(ActionEvent e) { //TODO Listeners de los radio buttons de los incisos
        if (e.getSource() == a) { //TODO Condicion para ver si el boton a es presionado
            if ((respuesta.compareTo("a")) == 0) { //TODO Si la respuesta de la pregunta es igual al nombre del boton haz esto:
                i++; //añade uno a la variable auxiliar (Número de pregunta)
                try {
                    siguientePregunta(); //lee la información del archivo para la siguiente pregunta
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                    pregunta.setText(añadirFacil(i)); //cambia el texto de la pregunta por el de la siguiente pregunta
                    vidaBoss--; //disminuye la vida del boss
                    Vida.setBounds(15, 505, ((vidaBoss * 615) / vidaBossInicial), 60); //Regla de 3 para la vida del boss
                }
            }
                else{ //TODO Si el usuario se equivoca de respuesta hace esto:
                    vidaPlayer--; //Disminuye la vida del player
                    VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60); //regla de 3 para l avida del player
                    if (vidaPlayer == 0) { //TODO Si la vida del player es 0, entonces llama a la funcion cerrar
                        cerrar();
                        try {
                            br.reset(); //TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.add(noSoyFuelte); //Añade la foto de derrota
                        mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                        mensaje.setBounds(50, 50, 700, 75);
                    }
                }
                if (i == 30) { //para cuando se conteste la última pregunta bien salga el frame de ganador y permita volver a jugar
                    Vida.setVisible(false);
                    VidaJ.setVisible(false);
                    cerrar();
                    try {
                        br.reset();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            if (e.getSource() == b) {//TODO Condicion para ver si el boton a es presionado
                if ((respuesta.compareTo("b")) == 0) {//TODO Si la respuesta de la pregunta es igual al nombre del boton haz esto:
                    i++;//añade uno a la variable auxiliar (Número de pregunta)
                    try {
                        siguientePregunta();//cambia el texto del textArea por el de la siguiente pregunta
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    pregunta.setText(añadirFacil(i));//cambia el texto de la pregunta por el de la siguiente pregunta
                    vidaBoss--;//disminuye la vida del boss
                    Vida.setBounds(15, 505, ((vidaBoss * 615) / vidaBossInicial), 60);//regla de 3 para la vida del boss
                } else {//TODO Si el usuario se equivoca de respuesta hace esto:
                    vidaPlayer--;//Disminuye la vida del player
                    VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);
                    if (vidaPlayer == 0) {//TODO Si la vida del player es 0, entonces llama a la funcion cerrar
                        cerrar();
                        try {
                            br.reset();//TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.add(noSoyFuelte);//Añade la foto de derrota
                        mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                        mensaje.setBounds(50, 50, 700, 75);
                    }
                }
                if (i == 30) {//para cuando se conteste la última pregunta bien salga el frame de ganador y permita volver a jugar
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
            if (e.getSource() == c) {//TODO Si la respuesta de la pregunta es igual al nombre del boton haz esto:
                if ((respuesta.compareTo("c")) == 0) {
                    i++;//añade uno a la variable auxiliar (Numero de pregunta)
                    try {
                        siguientePregunta();//Llama a la siguiente pregunta
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    pregunta.setText(añadirFacil(i));//cambia el texto del textArea por el de la siguiente pregunta
                    vidaBoss--;
                    Vida.setBounds(15, 505, ((vidaBoss * 615) / vidaBossInicial), 60);
                } else {
                    vidaPlayer--;//Disminuye la vida del player
                    VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);//regla de 3 para la vida del player
                    if (vidaPlayer == 0) {//TODO Si la vida del player es 0, entonces llama a la funcion cerrar
                        cerrar();
                        try {
                            br.reset();//TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.add(noSoyFuelte);//Añade la foto de derrota
                        mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                        mensaje.setBounds(50, 50, 700, 75);
                    }
                }
                if (i == 30) {//para cuando se conteste la última pregunta bien salga el frame de ganador y permita volver a jugar
                    Vida.setVisible(false);
                    VidaJ.setVisible(false);
                    cerrar();
                    try {
                        br.reset();//TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }


            if (e.getSource() == d) {
                if ((respuesta.compareTo("d")) == 0) {
                    i++;//añade uno a la variable auxiliar (Numero de pregunta)
                    try {
                        siguientePregunta();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    pregunta.setText(añadirFacil(i));
                    vidaBoss--;//Disminuye la vida del boss
                    Vida.setBounds(15, 505, ((vidaBoss * 615) / vidaBossInicial), 60);//regla de 3 para la vida del boss
                } else {
                    vidaPlayer--;//Disminuye la vida del player
                    VidaJ.setBounds(660, 20, ((vidaPlayer * 270) / vidaPlayerInicial), 60);//regla de 3 para la vida del player
                    if (vidaPlayer == 0) {//TODO Si la vida del player es 0, entonces llama a la funcion cerrar
                        cerrar();
                        try {
                            br.reset();//TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.add(noSoyFuelte);
                        mensaje.setText("Al parecer esta mazmorra te superó... no demostraste ser digno");
                        mensaje.setBounds(50, 50, 700, 75);
                    }
                }
                if (i == 30) {//para cuando se conteste la última pregunta bien salga el frame de ganador y permita volver a jugar
                    Vida.setVisible(false);
                    VidaJ.setVisible(false);
                    cerrar();
                    try {
                        br.reset();//TODO vuelve al documento en el checkpoint establecido (Vuelve a leer desde el inicio)
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        }


    public String añadirFacil(int i) {//TODO metodo para añadir la siguiente pregunta al textArea
        //TODO metodo para hacer los cambios de imagen, mostrar lore y cambio de canciones segun el boss
        if (i == 5) {//TODO Boss2  - poner su rola, imagen y el lore previo al boss
            clip.stop();//detiene la rola del primer boss
            GUI(); //Llama a la interfaz chica y oculta las cosas que tiene
            start.setVisible(false);
            //Pone el mensaje del lore
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
            continuar.addActionListener(e -> { //TODO Listener del continuar que pone todos los elementos del juego
                //TODO (Pregunta, opciones, imagen y vida)
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
            continuar.addActionListener(e -> { //TODO Listener para meter la siguiente rola
                try {
                    segundaCancion();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }

            });
            vidaBoss = 11; //TODO ponemos la vida del siguiente boss (11 porque le quita 1 de vida cada que se llama la funcion)
            //TODO de tal manera que cuando se llame se ponga en 10 (11 - 1 al ser llamada = 10)
            vidaBossInicial = 10; //vida del boss para la regla de 3 de la vida
            Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60); //regla de 3 para la barra de vida
        }
        if (i == 15) {//TODO Boss Chayanne- nos llama su rola, su respectiva imagen y el lore del Boss.
            clip.stop();//detiene la canción
            GUI();//llamamos la interfazz del inicio para aprovechar el componente mensaje y meter el lore del respectivo jefe, además se hace invisible lo innecesario.
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
            continuar.addActionListener(e -> {//TODO Listener del continuar que pone todos los elementos del juego
                //TODO (Pregunta, opciones, imagen y vida)
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

            continuar.addActionListener(e -> { //TODO listener para poner la rola de chayanne
                try {
                    terceraCancion();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            });
            vidaBossInicial = 15;
            vidaBoss = 16;//TODO ponemos la vida del siguiente boss (16 porque le quita 1 de vida cada que se llama la funcion)
            //TODO de tal manera que cuando se llame se ponga en 15 (16 - 1 al ser llamada = 15)
            Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60);
        }
        if(i ==20){//TODO si llegamos a la pregunta 20, chayanne se vuelve fase 2 (Cambia de imagen)
            imagen.setIcon(boss4);
        }
        if(i ==25) {//TODO si llegamos a la pregunta 25, chayanne se vuevle la fase 3 (Cambio de imagen)
            imagen.setIcon(boss5);
        }
        return (pregunt + "\n" + incisoA + "\n" + incisoB + "\n" + incisoC + "\n" + incisoD);

    }
    public Component cambiarImagen(){//TODO metodo para cambiar de imagen
        imagen=new JLabel();
        imagen.setBounds(0,0,650,500);
        png =new ImageIcon(getClass().getResource(nombreImagen));//Usamos la variable nombreImagen para solo hacer una simple
        //modificación cada que cambiemos de imagen
        imagen.setIcon(png);
        frame.add(imagen);
        imagen.setVisible(true);
        return imagen;
    }

    public void cerrar(){ //TODO metodo para el game over (Victoria y derrota)
        GUI();
        clip.stop();//detemos la rola
        start.setVisible(false);
        //Ponemos un mensaje de lore para la victoria
        mensaje.setText("Haz logrado derrotar al mismisimo diablo, con ello, reuniste la magia suficiente para teletransportarte a tu mundo, despiertas en el baño del edificio 5J y todo fue sueño... resultó que todo fue por culpa de unos papa boneless podridos que tenían hongos alucinógenos");
        JButton playAgain= new JButton("PLAY AGAIN");//Botón de volver a jugar
        playAgain.setBounds(300,315,200,80);
        noSoyFuelte = new JLabel();//Imagen de derrota
        noSoyFuelte.setBounds(250,50,400,300);
        noSoyFuelte.setIcon(GameOver);
        continuar = new JButton("EXIT");//Boton de exit
        continuar.setBounds(300, 425, 200, 80);
        frame.add(continuar);//Metemos los botones al frame
        frame.add(playAgain);
        //Hacemos invisible las demás cosas
        pregunta.setVisible(false);
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        imagen.setVisible(false);
        Vida.setVisible(false);
        VidaJ.setVisible(false);
        playAgain.addActionListener(e ->{//TODO listener del playAgain, desechamos el frame actual y llamamos al inicial
          frame.dispose();
          frame=new JFrame();
          GUI();
          vidaPlayer = 10;//Reiniciamos los valores de la vida del boss y del player
          vidaBoss = 5;
          vidaBossInicial=5;
          i = 0; //La variable del numero de pregunta la hacemos 0
          clip.close();//Quitamos la cancion
            try {//TODO ponemos la rola de Game of Thrones (La que sale cuando abres el juego)
                cancionLore();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });
        continuar.addActionListener(e -> System.exit(0));//TODO Boton de exit (Cierra el juego)
    }
    void cancionLore() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //TODO rola de Game of Thrones
        f = new File("GOTOpening.wav");//indicamos el archivo de la rola
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);//Le decimos a Java que es un archivo de audio
        clip.open(audioStream);//Abrimos el archivo
        clip.start();//Le damos play a la rola
    }
    /*
    TODO Las canciones son Hilos  //LEER AQUÍ//
    Los hilos se ejecutan al mismo tiempo que el programa lo hace, sin embargo las canciones se ejecutan en 2do plano
    si no tienes nada corriendo en el main NO SE ESCUCHARÁ LA ROLA, simplemente terminará el programa, debe de haber algún
    proceso en el main para que se escuche lo siguiente
    TODO //LEER AQUÍ//
     */
    void primerCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //TODO rola del primer boss
        f = new File("Gwyn.wav");//indicamos el archivo de la rola
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);//Le decimos a Java que es un archivo de audio
        clip.open(audioStream);//Abrimos el archivo
        clip.start();//Le damos play a la rola
    }
    void segundaCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //TODO rola del segundo boss
        f = new File("AbyssWatchers.wav");//indicamos el archivo de la rola
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);//Le decimos a Java que es un archivo de audio
        clip = AudioSystem.getClip();//Recibimos el archivo de audio para manejarlo con "Clip"
        clip.open(audioStream);//Abrimos el archivo
        clip.start();//Le damos play a la rola
    }
    void terceraCancion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //TODO rola de Chayanne
        f = new File("Vordt.wav");//indicamos el archivo de la rola
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);//Le decimos a Java que es un archivo de audio
        clip = AudioSystem.getClip();//Recibimos el archivo de audio para manejarlo con "Clip"
        clip.open(audioStream);//Abrimos el archivo
        clip.start();//Le damos play a la rola
    }
    public void vida(){
        //TODO Método para crear un JPanel que represente la vida del boss.
        Vida=new JPanel();
        Vida.setBounds(15,515,((vidaBoss*615)/vidaBossInicial),60);//Regla de 3 para sacar el ancho de la vida
        Vida.setBackground(Color.RED);
        Vida.setVisible(true);
        frame.add(Vida);
    }
    public void vidaPlayer(){
        //TODO Método para crear un JPanel que represente la vida del jugador.
        VidaJ=new JPanel();
        VidaJ.setBounds(660,20,((vidaPlayer*270)/vidaPlayerInicial),60);//Regla de 3 para sacar el ancho de la vida
        VidaJ.setBackground(Color.GREEN);
        VidaJ.setVisible(true);
        frame.add(VidaJ);
    }

}
