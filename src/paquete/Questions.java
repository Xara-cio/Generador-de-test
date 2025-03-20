/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package paquete;



import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;


/**
 *
 * @author User
 */
public class Questions extends javax.swing.JPanel {
    
    // Clase interna RoundedBorder
    class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(c.getBackground()); // Establecer el color del contorno al color de fondo
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius); // Dibujar el contorno redondeado
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    
    
    /**
     * Creates new form Questions
     */     
    public Questions() {                   
        initComponents();
        
        // Agregar márgenes a los JTextField
        Pregunta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        RCorrecta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        RIncorrecta1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        RIncorrecta2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        RIncorrecta3.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


        // Crear una instancia de RoundedBorder
        RoundedBorder roundedBorder = new RoundedBorder(10); // Ajusta el radio según sea necesario

        // Aplicar RoundedBorder a los JTextField
        Pregunta.setBorder(roundedBorder);
        RCorrecta.setBorder(roundedBorder);
        RIncorrecta1.setBorder(roundedBorder);
        RIncorrecta2.setBorder(roundedBorder);
        RIncorrecta3.setBorder(roundedBorder);

               
        // Escalar la imagen de FondoPregunta después de que se haya renderizado la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            escalarFondoPregunta();
        // Escalar las imágenes de los botones
            escalarBotones();               
        });   
        
        Btnmenos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnmenosMouseClicked(evt);
            }
        });    
    }   
    
    private void escalarBotones() {
        // Escalar la imagen de BtnMasOff
        Utilidades.SetImageLabel(Btnmenos, "src/images/Menos_Off.png", true); // Reemplaza con la ruta correcta si es diferente

    }     
    
    
    private void escalarFondoPregunta() {
        // Escalar la imagen de FondoPregunta
        Utilidades.SetImageLabel(FondoPregunta, "src/images/Cuadrado_Off.png", true); // Reemplaza con la ruta correcta si es diferente
    }  
    
    
    // Métodos para obtener los campos (getters)
    public JTextField getPregunta() {
        return Pregunta;
    }

    public JTextField getRCorrecta() {
        return RCorrecta;
    }

    public JTextField getRIncorrecta1() {
        return RIncorrecta1;
    }

    public JTextField getRIncorrecta2() {
        return RIncorrecta2;
    }

    public JTextField getRIncorrecta3() {
        return RIncorrecta3;
    }
    
    public String getPreguntaTexto() {
        return Pregunta.getText();
    }

    public String getRCorrectaTexto() {
        return RCorrecta.getText();
    }

    public String getRIncorrecta1Texto() {
        return RIncorrecta1.getText();
    }

    public String getRIncorrecta2Texto() {
        return RIncorrecta2.getText();
    }

    public String getRIncorrecta3Texto() {
        return RIncorrecta3.getText();
    }
    
    
    private void BtnmenosMouseClicked(java.awt.event.MouseEvent evt) {
        // Obtener el contenedor padre (BodyPanel)
        java.awt.Container parent = this.getParent();

        // Eliminar este panel (Questions) del contenedor padre
        parent.remove(this);

        // Actualizar la interfaz gráfica
        parent.revalidate();
        parent.repaint();

        // Asegurar que el tamaño del BodyPanel se ajuste
        try {
            Main.instance.ajustarAltoBody();
        } catch (NullPointerException e) {
            System.out.println("Error: Main.instance es null. " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pregunta = new javax.swing.JTextField();
        Btnmenos = new javax.swing.JLabel();
        RIncorrecta3 = new javax.swing.JTextField();
        RCorrecta = new javax.swing.JTextField();
        RIncorrecta1 = new javax.swing.JTextField();
        RIncorrecta2 = new javax.swing.JTextField();
        incorrecta = new javax.swing.JLabel();
        pregunta1 = new javax.swing.JLabel();
        correcta = new javax.swing.JLabel();
        FondoPregunta = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 51, 102));
        setForeground(new java.awt.Color(0, 51, 102));
        setMaximumSize(new java.awt.Dimension(430, 320));
        setMinimumSize(new java.awt.Dimension(430, 320));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(430, 320));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Pregunta.setBackground(new java.awt.Color(62, 77, 92));
        Pregunta.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        Pregunta.setForeground(new java.awt.Color(255, 255, 255));
        Pregunta.setMaximumSize(new java.awt.Dimension(64, 22));
        add(Pregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 280, 40));

        Btnmenos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Btnmenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Menos_Off.png"))); // NOI18N
        Btnmenos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Btnmenos.setMaximumSize(new java.awt.Dimension(20, 20));
        Btnmenos.setMinimumSize(new java.awt.Dimension(20, 20));
        Btnmenos.setPreferredSize(new java.awt.Dimension(20, 20));
        add(Btnmenos, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        RIncorrecta3.setBackground(new java.awt.Color(62, 77, 92));
        RIncorrecta3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        RIncorrecta3.setForeground(new java.awt.Color(255, 255, 255));
        RIncorrecta3.setMaximumSize(new java.awt.Dimension(71, 22));
        RIncorrecta3.setMinimumSize(new java.awt.Dimension(71, 22));
        RIncorrecta3.setPreferredSize(new java.awt.Dimension(280, 30));
        RIncorrecta3.setSelectionColor(new java.awt.Color(51, 102, 255));
        add(RIncorrecta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        RCorrecta.setBackground(new java.awt.Color(62, 77, 92));
        RCorrecta.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        RCorrecta.setForeground(new java.awt.Color(255, 255, 255));
        RCorrecta.setMaximumSize(new java.awt.Dimension(71, 22));
        RCorrecta.setMinimumSize(new java.awt.Dimension(71, 22));
        RCorrecta.setPreferredSize(new java.awt.Dimension(71, 22));
        add(RCorrecta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 280, 30));

        RIncorrecta1.setBackground(new java.awt.Color(62, 77, 92));
        RIncorrecta1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        RIncorrecta1.setForeground(new java.awt.Color(255, 255, 255));
        RIncorrecta1.setMaximumSize(new java.awt.Dimension(71, 22));
        RIncorrecta1.setMinimumSize(new java.awt.Dimension(71, 22));
        RIncorrecta1.setPreferredSize(new java.awt.Dimension(71, 22));
        add(RIncorrecta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 280, 30));

        RIncorrecta2.setBackground(new java.awt.Color(62, 77, 92));
        RIncorrecta2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        RIncorrecta2.setForeground(new java.awt.Color(255, 255, 255));
        RIncorrecta2.setMaximumSize(new java.awt.Dimension(280, 30));
        RIncorrecta2.setMinimumSize(new java.awt.Dimension(280, 30));
        RIncorrecta2.setPreferredSize(new java.awt.Dimension(280, 30));
        add(RIncorrecta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        incorrecta.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        incorrecta.setForeground(new java.awt.Color(255, 255, 255));
        incorrecta.setText("Respuesta Incorrecta");
        add(incorrecta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 170, 20));

        pregunta1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        pregunta1.setForeground(new java.awt.Color(255, 255, 255));
        pregunta1.setText("Pregunta");
        add(pregunta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 70, -1));

        correcta.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        correcta.setForeground(new java.awt.Color(255, 255, 255));
        correcta.setText("Respuesta Correcta");
        add(correcta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 140, -1));

        FondoPregunta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoPregunta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cuadrado_Off.png"))); // NOI18N
        FondoPregunta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FondoPregunta.setMaximumSize(new java.awt.Dimension(200, 200));
        FondoPregunta.setMinimumSize(new java.awt.Dimension(200, 200));
        FondoPregunta.setPreferredSize(new java.awt.Dimension(200, 200));
        add(FondoPregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 360, 270));
        FondoPregunta.getAccessibleContext().setAccessibleName("");

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fondo.png"))); // NOI18N
        Fondo.setText("jLabel4");
        Fondo.setMaximumSize(new java.awt.Dimension(430, 400));
        Fondo.setMinimumSize(new java.awt.Dimension(430, 400));
        Fondo.setPreferredSize(new java.awt.Dimension(430, 400));
        add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Btnmenos;
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel FondoPregunta;
    private javax.swing.JTextField Pregunta;
    private javax.swing.JTextField RCorrecta;
    private javax.swing.JTextField RIncorrecta1;
    private javax.swing.JTextField RIncorrecta2;
    private javax.swing.JTextField RIncorrecta3;
    private javax.swing.JLabel correcta;
    private javax.swing.JLabel incorrecta;
    private javax.swing.JLabel pregunta1;
    // End of variables declaration//GEN-END:variables
}
