/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paquete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
//import paquete.InfoPanel;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 *
 * @author User
 */
public class Main extends javax.swing.JFrame {
    public static Main instance;
    private final ArrayList<Pregunta> listaPreguntas;
    private JLabel debugLabel; // Agregar JLabel para el texto de debug

      
    /**
     * Creates new form Main
     */
    public Main() {
        instance = this; // Guardar la instancia al iniciar                             
        initComponents();
        
        // Escalar las imágenes de los botones
        escalarBotones();        
        
        // Escalar la imagen de InfoText después de que se haya renderizado la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            escalarInfoText();
        });
        
        // Paso 1: Agregar el ActionListener al JComboBox
        ComboBoxdesplegable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar a la función para leer el CSV y mostrar las preguntas
                leerCSVYMostrarPreguntas();
                BodyPanel.revalidate();
                BodyPanel.repaint();                
            }
        });
        
        listaPreguntas = new ArrayList<>();// instanciar listado preguntas
        
        // Cambiar el layout del Body a BoxLayout
        BodyPanel.setLayout(new BoxLayout(BodyPanel, BoxLayout.Y_AXIS));
         
        // Agregar el panel de preguntas al contenedor principal
        System.out.println("Panel de preguntas agregado al contenedor.");
        ScrollBody.setViewportView(BodyPanel); // Asegurar que el Body esté en el ScrollPane
        ScrollBody.revalidate();
        ScrollBody.repaint();        
        
        
        GuardarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                guardarCSV(listaPreguntas);
            }
        });
        
        //Agregar un MouseListener al botón "Añadir pregunta"
        BtnMasOff.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarNuevaPregunta();
            }
        });
        
        BtnInfoOff.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InfoPanel.setVisible(!InfoPanel.isVisible()); // Cambiar la visibilidad del panel               
            }
        });
       
        CrearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                actualizarCSV(listaPreguntas);
                comprimirEnZIP();
            }
        }); 
        
        //BOTÓN EJECUTAR
        EjecutarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                List<Pregunta> listaPreguntas = obtenerListaPreguntas();
                System.out.println("Número de preguntas obtenidas: " + listaPreguntas.size());
                actualizarCSV(listaPreguntas);
            }
        });   
        
        // Inicializar el JLabel de debug
        debugLabel = new JLabel();
        Footer.add(debugLabel); // Agregar el JLabel al Footer o a un panel separado        
    
    }
    
    private void escalarBotones() {
        // Escalar la imagen de BtnMasOff
        Utilidades.SetImageLabel(BtnMasOff, "src/images/Mas_Off.png", true); // Reemplaza con la ruta correcta si es diferente

        // Escalar la imagen de BtnInfoOff
        Utilidades.SetImageLabel(BtnInfoOff, "src/images/Info_Off.png", true); // Reemplaza con la ruta correcta si es diferente
    }    
    
    private void escalarInfoText() {
        // Escalar la imagen de InfoText
        Utilidades.SetImageLabel(InfoText, "src/images/Panel_Info.png", true); // Reemplaza con la ruta correcta si es diferente
    }
        
    //AÑADIR NUEVA PREGUNTA
    private void agregarNuevaPregunta() {
        Questions nuevaPregunta = new Questions();
        BodyPanel.add(nuevaPregunta);
        ajustarAltoBody();
        BodyPanel.revalidate();
        BodyPanel.repaint();
        mostrarRegistro("Pregunta añadida (actualmente " + BodyPanel.getComponentCount() + ")");
    }
    
    //ELIMINAR UNA PREGUNTA
    public void eliminarPregunta(Questions pregunta) {
        BodyPanel.remove(pregunta);
        ajustarAltoBody();
        BodyPanel.revalidate();
        BodyPanel.repaint();
        mostrarRegistro("Pregunta eliminada (quedan " + BodyPanel.getComponentCount() + ")");
    } 
    
    // Método para leer el CSV y mostrar las preguntas
    private void leerCSVYMostrarPreguntas() {
        // Eliminar solo las instancias de Questions
        Component[] components = BodyPanel.getComponents();
        for (Component component : components) {
            if (component instanceof Questions) {
                BodyPanel.remove(component);
            }
        }
        
        listaPreguntas.clear();
       
        
        // Ruta del archivo CSV (ajusta la ruta al archivo correcto)
        String archivoCSV = "src/paquete/Preguntas.csv";

        try {
            // Paso 2: Usar la función readFile de Utilidades para leer el archivo
            String contenidoCSV = Utilidades.readFileAsString(archivoCSV);

            // Paso 3: Procesar el contenido del CSV
            String[] lineas = contenidoCSV.split("\n"); // Se asume que cada línea es un registro

            for (String linea : lineas) {
                // Paso 4: Separar los valores de cada línea
                String[] columnas = linea.split(";"); // Suponiendo que las columnas están separadas por punto y coma

                if (columnas.length >= 5) {
                    // Crear un objeto Pregunta con la información del CSV
                    Pregunta pregunta = new Pregunta(columnas[0], columnas[1], columnas[2], columnas[3], columnas[4]);
                    // Añadir la pregunta a la lista
                    listaPreguntas.add(pregunta);
                }
            }

            // Verificar que las preguntas se han agregado correctamente
            System.out.println("Número de preguntas cargadas: " + listaPreguntas.size());
            
            // Mostrar la primera pregunta si hay preguntas
            if (!listaPreguntas.isEmpty()) {
                for (Pregunta pregunta : listaPreguntas){
                    mostrarPregunta(pregunta);                   
                }
                ajustarAltoBody(); // Llamar a ajustarAltoBody()
            } else {
                System.out.println("No se encontraron preguntas en el archivo.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }
    
// Método para mostrar las preguntas en los JTextFields
    private void mostrarPregunta(Pregunta pregunta) {


            System.out.println("Mostrando pregunta: " + pregunta.getPregunta()); // Imprimir pregunta
            
            Questions questionsPanel = new Questions();    
            
            // Rellenar los JTextFields con la pregunta y las respuestas
            questionsPanel.getPregunta().setText(pregunta.getPregunta());
            questionsPanel.getRCorrecta().setText(pregunta.getRCorrecta());
            questionsPanel.getRIncorrecta1().setText(pregunta.getRIncorrecta1());
            questionsPanel.getRIncorrecta2().setText(pregunta.getRIncorrecta2());
            questionsPanel.getRIncorrecta3().setText(pregunta.getRIncorrecta3());

            // Imprimir las respuestas para verificar que se han volcado correctamente
            System.out.println("Respuesta Correcta: " + pregunta.getRCorrecta());
            System.out.println("Respuesta Incorrecta 1: " + pregunta.getRIncorrecta1());
            System.out.println("Respuesta Incorrecta 2: " + pregunta.getRIncorrecta2());
            System.out.println("Respuesta Incorrecta 3: " + pregunta.getRIncorrecta3());
            
            BodyPanel.add(questionsPanel);
    }
    
    
    public void ajustarAltoBody() {
        int alturaTotal = 0;
        for (Component component : BodyPanel.getComponents()) {
            alturaTotal += component.getPreferredSize().height;
        }
        BodyPanel.setPreferredSize(new Dimension(BodyPanel.getPreferredSize().width, alturaTotal));
        BodyPanel.revalidate();
        BodyPanel.repaint();
    }
    
 //CREAR LISTA DE PREGUNTAS
    private List<Pregunta> obtenerListaPreguntas() {
        List<Pregunta> listaPreguntas = new ArrayList<>();
        Component[] components = BodyPanel.getComponents();
        for (Component component : components) {
            if (component instanceof Questions) {
                Questions questionsPanel = (Questions) component;
                // Obtener los valores de los JTextFields y crear un objeto Pregunta
                String pregunta = questionsPanel.getPregunta().getText();
                String rCorrecta = questionsPanel.getRCorrecta().getText();
                String rIncorrecta1 = questionsPanel.getRIncorrecta1().getText();
                String rIncorrecta2 = questionsPanel.getRIncorrecta2().getText();
                String rIncorrecta3 = questionsPanel.getRIncorrecta3().getText();
                Pregunta preguntaObj = new Pregunta(pregunta, rCorrecta, rIncorrecta1, rIncorrecta2, rIncorrecta3);
                listaPreguntas.add(preguntaObj);
            }
        }
        return listaPreguntas;
    }
    
    //ACTUALIZAR CSV
    private void actualizarCSV(List<Pregunta> listaPreguntas) {
    String archivoCSV = "src/paquete/Preguntas.csv";
    try (PrintWriter writer = new PrintWriter(archivoCSV)) {
        for (Pregunta pregunta : listaPreguntas) {
            String linea = pregunta.getPregunta() + ";" + pregunta.getRCorrecta() + ";" + pregunta.getRIncorrecta1() + ";" + pregunta.getRIncorrecta2() + ";" + pregunta.getRIncorrecta3();
            writer.println(linea);
        }
        System.out.println("Archivo CSV actualizado correctamente.");
        mostrarConfirmacion("Las preguntas han sido guardadas (" + listaPreguntas.size() + " en total)");
    } catch (IOException e) {
        System.out.println("Error al actualizar el archivo CSV: " + e.getMessage());
        mostrarError("No se pudo crear el archivo de preguntas");
    }
    }
    
    //GUARDAR CSV
    private void guardarCSV(List<Pregunta> listaPreguntas) {
        String archivoCSV = "src/paquete/Preguntas.csv";
        try (PrintWriter writer = new PrintWriter(archivoCSV)) {
            for (Pregunta pregunta : listaPreguntas) {
                String linea = pregunta.getPregunta() + ";" + pregunta.getRCorrecta() + ";" + pregunta.getRIncorrecta1() + ";" + pregunta.getRIncorrecta2() + ";" + pregunta.getRIncorrecta3();
                writer.println(linea);
            }
            System.out.println("Archivo CSV guardado correctamente.");
            mostrarConfirmacion("Las preguntas han sido guardadas (" + listaPreguntas.size() + " en total)");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV: " + e.getMessage());
            mostrarError("No se pudo crear el archivo de preguntas");
          }
    }

    //COMPRIMIR ZIP
    private void comprimirEnZIP() {
        String archivoCSV = "src/paquete/Preguntas.csv";
        String archivoZIP = "src/paquete/Preguntas.zip";
        try (FileOutputStream fos = new FileOutputStream(archivoZIP);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            // Agregar el archivo CSV al ZIP
            ZipEntry zipEntry = new ZipEntry(Paths.get(archivoCSV).getFileName().toString());
            zos.putNextEntry(zipEntry);
            Files.copy(Paths.get(archivoCSV), zos);
            zos.closeEntry();
            System.out.println("Archivo ZIP creado correctamente.");
            mostrarConfirmacion("Las preguntas se guardaron y se exportó el simulador");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo ZIP: " + e.getMessage());
            mostrarAviso("Las preguntas se guardaron, pero no se pudo comprimir el simulador");
        }
    }
    
    //MOSTRAR MENSAJES DEBUG
    private void mostrarMensaje(String mensaje, Color color, int duracion) {
    debugLabel.setForeground(color);
    debugLabel.setText(mensaje);
    Timer timer = new Timer(duracion * 1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            debugLabel.setText(""); // Borrar el mensaje después de la duración
        }
    });
    timer.setRepeats(false);
    timer.start();
}

    private void mostrarError(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xEB4151), 3); // Rojo #EB4151, 3 segundos
    }

    private void mostrarAviso(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xFF9C00), 2); // Amarillo #FF9C00, 2 segundos
    }

    private void mostrarConfirmacion(String mensaje) {
        mostrarMensaje(mensaje, new Color(0x86D295), 2); // Verde #86D295, 2 segundos
    }

    private void mostrarRegistro(String mensaje) {
        mostrarMensaje(mensaje, new Color(0xF7F7F7), 1); // Blanco #F7F7F7, 1 segundo
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelFondo = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        Tittle = new javax.swing.JLabel();
        ScrollBody = new javax.swing.JScrollPane();
        BodyPanel = new javax.swing.JPanel();
        SmallBody = new javax.swing.JPanel();
        Subtittle = new javax.swing.JLabel();
        ComboBoxdesplegable = new javax.swing.JComboBox<>();
        Añadirpregunta = new javax.swing.JLabel();
        BtnMasOff = new javax.swing.JLabel();
        BtnInfoOff = new javax.swing.JLabel();
        InfoPanel = new javax.swing.JPanel();
        InfoText = new javax.swing.JLabel();
        FondoBody = new javax.swing.JLabel();
        Footer = new javax.swing.JPanel();
        CrearBtn = new javax.swing.JLabel();
        GuardarBtn = new javax.swing.JLabel();
        EjecutarBtn = new javax.swing.JLabel();
        Debug = new javax.swing.JLabel();
        FondoBase = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(430, 932));
        setMinimumSize(new java.awt.Dimension(430, 932));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        PanelFondo.setMaximumSize(new java.awt.Dimension(430, 932));
        PanelFondo.setMinimumSize(new java.awt.Dimension(430, 932));
        PanelFondo.setOpaque(false);
        PanelFondo.setPreferredSize(new java.awt.Dimension(430, 932));
        PanelFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setMaximumSize(new java.awt.Dimension(430, 150));
        Header.setMinimumSize(new java.awt.Dimension(430, 150));
        Header.setOpaque(false);
        Header.setPreferredSize(new java.awt.Dimension(430, 150));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelFondo.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 50));

        Tittle.setFont(new java.awt.Font("Raleway Medium", 0, 24)); // NOI18N
        Tittle.setForeground(new java.awt.Color(247, 247, 247));
        Tittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tittle.setText("Crea tu simulador teórico");
        Tittle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PanelFondo.add(Tittle, new org.netbeans.lib.awtextra.AbsoluteConstraints(-3, 5, 430, 40));

        ScrollBody.setBackground(new java.awt.Color(60, 63, 65));
        ScrollBody.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollBody.setMaximumSize(new java.awt.Dimension(430, 620));
        ScrollBody.setMinimumSize(new java.awt.Dimension(430, 620));
        ScrollBody.setOpaque(false);
        ScrollBody.setPreferredSize(new java.awt.Dimension(430, 620));

        BodyPanel.setBackground(new java.awt.Color(0, 51, 102));
        BodyPanel.setMaximumSize(new java.awt.Dimension(430, 620));
        BodyPanel.setMinimumSize(new java.awt.Dimension(430, 620));
        BodyPanel.setOpaque(false);
        BodyPanel.setPreferredSize(new java.awt.Dimension(430, 620));
        BodyPanel.setRequestFocusEnabled(false);

        SmallBody.setMaximumSize(new java.awt.Dimension(430, 250));
        SmallBody.setMinimumSize(new java.awt.Dimension(430, 250));
        SmallBody.setOpaque(false);
        SmallBody.setPreferredSize(new java.awt.Dimension(430, 250));
        SmallBody.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Subtittle.setFont(new java.awt.Font("Raleway", 0, 14)); // NOI18N
        Subtittle.setForeground(new java.awt.Color(247, 247, 247));
        Subtittle.setText("Tipo de Simulador");
        SmallBody.add(Subtittle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 290, 30));

        ComboBoxdesplegable.setBackground(new java.awt.Color(67, 83, 94));
        ComboBoxdesplegable.setFont(new java.awt.Font("Raleway Medium", 0, 12)); // NOI18N
        ComboBoxdesplegable.setForeground(new java.awt.Color(255, 255, 255));
        ComboBoxdesplegable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ahora Aprendo", "El Cazador", "Atrapa los Univercoins", "BAAM", "PiensoPalabra" }));
        ComboBoxdesplegable.setMaximumSize(new java.awt.Dimension(158, 21));
        SmallBody.add(ComboBoxdesplegable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 350, 70));

        Añadirpregunta.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        Añadirpregunta.setForeground(new java.awt.Color(255, 255, 255));
        Añadirpregunta.setText("Añadir una pregunta");
        SmallBody.add(Añadirpregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        BtnMasOff.setBackground(new java.awt.Color(30, 30, 30));
        BtnMasOff.setForeground(new java.awt.Color(221, 221, 221));
        BtnMasOff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnMasOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Mas_Off.png"))); // NOI18N
        BtnMasOff.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnMasOff.setMaximumSize(new java.awt.Dimension(30, 20));
        BtnMasOff.setMinimumSize(new java.awt.Dimension(30, 20));
        BtnMasOff.setPreferredSize(new java.awt.Dimension(30, 20));
        SmallBody.add(BtnMasOff, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, -1, -1));

        BtnInfoOff.setBackground(new java.awt.Color(30, 30, 30));
        BtnInfoOff.setForeground(new java.awt.Color(221, 221, 221));
        BtnInfoOff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnInfoOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Info_Off.png"))); // NOI18N
        BtnInfoOff.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnInfoOff.setMaximumSize(new java.awt.Dimension(30, 20));
        BtnInfoOff.setMinimumSize(new java.awt.Dimension(30, 20));
        BtnInfoOff.setPreferredSize(new java.awt.Dimension(30, 20));
        SmallBody.add(BtnInfoOff, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        InfoPanel.setMaximumSize(new java.awt.Dimension(320, 80));
        InfoPanel.setMinimumSize(new java.awt.Dimension(320, 80));
        InfoPanel.setOpaque(false);
        InfoPanel.setPreferredSize(new java.awt.Dimension(320, 80));
        InfoPanel.setLayout(new java.awt.BorderLayout());

        InfoText.setFont(new java.awt.Font("Raleway", 0, 10)); // NOI18N
        InfoText.setForeground(new java.awt.Color(255, 255, 255));
        InfoText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        InfoText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Panel_Info.png"))); // NOI18N
        InfoText.setText("<html><div style='padding: 10px;'>Añade las preguntas que quieras que aparezcan durante la simulación.<br>Luego, pulsa el botón crear para exportar el archivo zip que deberás subir a SharePoint.</div></html>");
        InfoText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        InfoText.setMaximumSize(new java.awt.Dimension(300, 30));
        InfoText.setMinimumSize(new java.awt.Dimension(300, 30));
        InfoText.setPreferredSize(new java.awt.Dimension(300, 30));
        InfoPanel.add(InfoText, java.awt.BorderLayout.CENTER);

        InfoPanel.setVisible(false);

        SmallBody.add(InfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, 80));

        FondoBody.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoBody.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fondo.png"))); // NOI18N
        FondoBody.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        FondoBody.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FondoBody.setMaximumSize(new java.awt.Dimension(430, 620));
        FondoBody.setMinimumSize(new java.awt.Dimension(430, 620));
        FondoBody.setPreferredSize(new java.awt.Dimension(430, 620));
        SmallBody.add(FondoBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 620));

        javax.swing.GroupLayout BodyPanelLayout = new javax.swing.GroupLayout(BodyPanel);
        BodyPanel.setLayout(BodyPanelLayout);
        BodyPanelLayout.setHorizontalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SmallBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BodyPanelLayout.setVerticalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addComponent(SmallBody, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 370, Short.MAX_VALUE))
        );

        ScrollBody.setViewportView(BodyPanel);

        PanelFondo.add(ScrollBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

        Footer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Footer.setMaximumSize(new java.awt.Dimension(430, 260));
        Footer.setMinimumSize(new java.awt.Dimension(430, 260));
        Footer.setOpaque(false);
        Footer.setPreferredSize(new java.awt.Dimension(430, 260));

        CrearBtn.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        CrearBtn.setForeground(new java.awt.Color(16, 24, 32));
        CrearBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CrearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Boton_On.png"))); // NOI18N
        CrearBtn.setText("Crear");
        CrearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CrearBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        CrearBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        CrearBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(CrearBtn);

        GuardarBtn.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        GuardarBtn.setForeground(new java.awt.Color(16, 24, 32));
        GuardarBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GuardarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Boton_On.png"))); // NOI18N
        GuardarBtn.setText("Guardar");
        GuardarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GuardarBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        GuardarBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        GuardarBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(GuardarBtn);

        EjecutarBtn.setFont(new java.awt.Font("Raleway Medium", 0, 14)); // NOI18N
        EjecutarBtn.setForeground(new java.awt.Color(16, 24, 32));
        EjecutarBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EjecutarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Boton_On.png"))); // NOI18N
        EjecutarBtn.setText("Ejecutar");
        EjecutarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EjecutarBtn.setMaximumSize(new java.awt.Dimension(430, 72));
        EjecutarBtn.setMinimumSize(new java.awt.Dimension(430, 72));
        EjecutarBtn.setPreferredSize(new java.awt.Dimension(430, 72));
        Footer.add(EjecutarBtn);

        Debug.setFont(new java.awt.Font("Raleway", 0, 8)); // NOI18N
        Debug.setForeground(new java.awt.Color(255, 255, 255));
        Debug.setToolTipText("");
        Footer.add(Debug);

        PanelFondo.add(Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 670, -1, -1));

        FondoBase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fondo.png"))); // NOI18N
        FondoBase.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FondoBase.setMaximumSize(new java.awt.Dimension(430, 932));
        FondoBase.setMinimumSize(new java.awt.Dimension(430, 932));
        FondoBase.setPreferredSize(new java.awt.Dimension(430, 932));
        PanelFondo.add(FondoBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Añadirpregunta;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JLabel BtnInfoOff;
    private javax.swing.JLabel BtnMasOff;
    private javax.swing.JComboBox<String> ComboBoxdesplegable;
    private javax.swing.JLabel CrearBtn;
    private javax.swing.JLabel Debug;
    private javax.swing.JLabel EjecutarBtn;
    private javax.swing.JLabel FondoBase;
    private javax.swing.JLabel FondoBody;
    private javax.swing.JPanel Footer;
    private javax.swing.JLabel GuardarBtn;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JLabel InfoText;
    private javax.swing.JPanel PanelFondo;
    private javax.swing.JScrollPane ScrollBody;
    private javax.swing.JPanel SmallBody;
    private javax.swing.JLabel Subtittle;
    private javax.swing.JLabel Tittle;
    // End of variables declaration//GEN-END:variables

}
