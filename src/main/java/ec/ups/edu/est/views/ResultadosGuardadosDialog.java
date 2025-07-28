package ec.ups.edu.est.views;

import ec.ups.edu.est.models.AlgorithmResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ResultadosGuardadosDialog extends JDialog {

    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final List<AlgorithmResult> resultados;

    public ResultadosGuardadosDialog(JFrame parent) {
        super(parent, "📊 Resultados Guardados", true);
        setSize(650, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        resultados = new ArrayList<>();

        modelo = new DefaultTableModel(new Object[]{"Algoritmo", "Pasos", "Tiempo (ms)", "Fecha"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnGraficar = new JButton("📈 Graficar Resultados");
        JButton btnLimpiar = new JButton("🧹 Limpiar Resultados");
        JButton btnCerrar = new JButton("❌ Cerrar");

        btnGraficar.addActionListener(e -> mostrarGrafica());
        btnLimpiar.addActionListener(e -> limpiarResultados());
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGraficar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void agregarResultado(AlgorithmResult r) {
        resultados.add(r);
        modelo.addRow(new Object[]{
                r.getNombreAlgoritmo(),
                r.getPasos(),
                r.getTiempoEjecucion(),
                r.getFecha()
        });
    }

    public void guardarResultadosEnCSV(String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(rutaArchivo)) {
            writer.println("Algoritmo,Pasos,Tiempo (ms),Fecha");
            for (AlgorithmResult r : resultados) {
                writer.printf("%s,%d,%d,%s%n",
                        r.getNombreAlgoritmo(),
                        r.getPasos(),
                        r.getTiempoEjecucion(),
                        r.getFecha());
            }
            System.out.println("✅ Resultados guardados en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar resultados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarResultados() {
        resultados.clear();
        modelo.setRowCount(0);
    }

    private void mostrarGrafica() {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay resultados para graficar.");
            return;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (AlgorithmResult r : resultados) {
            dataset.addValue(r.getTiempoEjecucion(), "Tiempo (ms)", r.getNombreAlgoritmo());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Tiempos de Ejecución por Algoritmo",
                "Algoritmo",
                "Tiempo (ms)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JDialog graficoDialog = new JDialog(this, "📈 Gráfica de Tiempos", true);
        graficoDialog.setSize(600, 400);
        graficoDialog.setLocationRelativeTo(this);
        graficoDialog.add(chartPanel);
        graficoDialog.setVisible(true);

    }
}

