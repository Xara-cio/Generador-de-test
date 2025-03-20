/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class Utilidades {       
        public static void ShowPanel (JPanel contenedor, JPanel contenido){
        contenido.setSize (683, 424);
        contenido.setLocation (0, 0);
        
//        contenedor.removeAll();
        contenedor.add(contenido, FlowLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }
        
        public static void SetImageLabel(JLabel labelName, String root, Boolean keepProportions) {
            // Crear un ImageIcon desde la ruta proporcionada
            ImageIcon image = new ImageIcon(root);

            // Obtener la imagen original del ImageIcon
            Image img = image.getImage(); 

            // Verificar si se debe mantener la proporción de la imagen
            if (keepProportions) {
                // Obtener el tamaño del JLabel
                int labelWidth = labelName.getWidth();
                int labelHeight = labelName.getHeight();

                // Obtener el ancho y alto de la imagen original
                int imgWidth = img.getWidth(null);
                int imgHeight = img.getHeight(null);

                // Calcular la relación de aspecto de la imagen original (ancho / alto)
                double aspectRatio = (double) imgWidth / imgHeight;

                int newWidth, newHeight;

                // Comparar las proporciones del JLabel y la imagen para ajustar dimensiones
                if ((double) labelWidth / labelHeight > aspectRatio) {
                    // Si el JLabel es más ancho en proporción, ajustar el alto y calcular el ancho
                    newHeight = labelHeight;
                    newWidth = (int) (labelHeight * aspectRatio);
                } else {
                    // Si el JLabel es más alto en proporción, ajustar el ancho y calcular el alto
                    newWidth = labelWidth;
                    newHeight = (int) (labelWidth / aspectRatio);
                }

                // Escalar la imagen con las nuevas dimensiones calculadas
                Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // Asignar la imagen escalada al JLabel
                labelName.setIcon(new ImageIcon(scaledImg));
            } else {
                // Si no se debe mantener la proporción, escalar la imagen al tamaño del JLabel
                Image scaledImg = img.getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_SMOOTH);

                // Asignar la imagen escalada al JLabel
                labelName.setIcon(new ImageIcon(scaledImg));
            }

            // Asegurarse de que el JLabel se redibuje para reflejar los cambios
            labelName.repaint();
        }
                               
        public static void SetImageLabel(JLabel labelName, String root, Dimension dimension, boolean keepProportions) {
            // Crear un ImageIcon desde la ruta proporcionada
            ImageIcon image = new ImageIcon(root);

            // Obtener la imagen original del ImageIcon
            Image img = image.getImage(); 

            // Verificar si se debe mantener la proporción de la imagen
            if (keepProportions) {
                // Obtener el tamaño del JLabel
                int labelWidth = dimension.width;
                int labelHeight = dimension.height;

                // Obtener el ancho y alto de la imagen original
                int imgWidth = img.getWidth(null);
                int imgHeight = img.getHeight(null);

                // Calcular la relación de aspecto de la imagen original (ancho / alto)
                double aspectRatio = (double) imgWidth / imgHeight;

                int newWidth, newHeight;

                // Comparar las proporciones del JLabel y la imagen para ajustar dimensiones
                if ((double) labelWidth / labelHeight > aspectRatio) {
                    // Si el JLabel es más ancho en proporción, ajustar el alto y calcular el ancho
                    newHeight = labelHeight;
                    newWidth = (int) (labelHeight * aspectRatio);
                } else {
                    // Si el JLabel es más alto en proporción, ajustar el ancho y calcular el alto
                    newWidth = labelWidth;
                    newHeight = (int) (labelWidth / aspectRatio);
                }

                // Escalar la imagen con las nuevas dimensiones calculadas
                Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // Asignar la imagen escalada al JLabel
                labelName.setIcon(new ImageIcon(scaledImg));
            } else {
                // Si no se debe mantener la proporción, escalar la imagen al tamaño del JLabel
                Image scaledImg = img.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);

                // Asignar la imagen escalada al JLabel
                labelName.setIcon(new ImageIcon(scaledImg));
            }

            // Asegurarse de que el JLabel se redibuje para reflejar los cambios
            labelName.repaint();
        }
        
        //FUNCIÓN PARA ESTABLECER UNA IMAGEN EN UN JLABEL CON AJUSTE AUTOMÁTICO DE LA IMAGEN AL JLABEL 
            public static void SetImageLabel(JLabel labelName, String root) {
            ImageIcon image = new ImageIcon(root);
            Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
            labelName.setIcon (icon);
            labelName.repaint();
        }
        
        // Función para generar la lista
        public static ArrayList<String> CreateStringList(String root, String name, String filetype, int size) {
            // Crear una lista para almacenar las rutas
            ArrayList<String> fileList = new ArrayList<>();

            // bucle for para generar las rutas
            for (int i = 0; i < size; i++) {
                // ruta del archivo usando los parámetros
                String filePath = root + "/" + name + i + "." + filetype;
                // Agregar la ruta a la lista
                fileList.add(filePath);
            }

            // Devolver la lista 
            return fileList;
        }
        
        // Función para leer y pasar a una cadena de texo
        public static String readFileAsString(String filePath) throws IOException {
            StringBuilder content = new StringBuilder(); // StringBuilder para almacenar el contenido del archivo

            // bloque para asegurarnos que BufferedReader se cierre automáticamente al final
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line; //// Definimos una variable para almacenar cada línea leída del archivo.
                while ((line = reader.readLine()) != null) { // // Lee archivo línea por línea hasta que no haya más(null es el fin de archivo).
                    content.append(line).append("\n"); // Agregar cada línea al StringBuilder y un salto de línea
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());// Si ocurre un error, no lo lee por ejemplo
            }
            return content.toString(); // Devuelve el contenido como cadena de texto
        }
        
        public static void writeFileAsString(String filePath, String content) throws IOException {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(content);
            }
        }        
}
