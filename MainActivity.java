package com.pmm.puzlebotones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button[][] botones = new Button[3][3];
    private int colorOriginal;
    private int colorSecundario;

    private int contar2;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        colorOriginal= getColor(R.color.YELLOW);
        colorSecundario = getColor(R.color.BLUE);

        // Contenedor principal - AUMENTAR a 5 filas para acomodar el nuevo botón
        GridLayout layout = new GridLayout(this);
        layout.setRowCount(5); // 3 filas para botones + 1 para reiniciar + 1 para el nuevo botón
        layout.setColumnCount(3);

        // Crear botones 3x3
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                Button b = new Button(this);
                b.setBackgroundColor(colorOriginal);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(fila);
                params.columnSpec = GridLayout.spec(col);
                params.width = 250;
                params.height = 250;
                params.setMargins(8, 8, 8, 8);

                layout.addView(b, params);
                botones[fila][col] = b;

                int finalFila = fila;
                int finalCol = col;

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cambiarColor(finalFila, finalCol);
                        comprobarVictoria();
                        contar2++;
                    }
                });
            }
        }

        // ===== BOTÓN REINICIAR =====
        Button reiniciar = new Button(this);
        reiniciar.setText("Reiniciar");

        GridLayout.LayoutParams paramsReiniciar = new GridLayout.LayoutParams();
        paramsReiniciar.rowSpec = GridLayout.spec(3); // Fila 3
        paramsReiniciar.columnSpec = GridLayout.spec(0, 3); // Ocupa 3 columnas
        paramsReiniciar.width = GridLayout.LayoutParams.MATCH_PARENT;
        paramsReiniciar.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramsReiniciar.setMargins(16, 16, 16, 16);

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });

        layout.addView(reiniciar, paramsReiniciar);

        // ===== SEGUNDO BOTÓN =====
        Button b = new Button(this);
        b.setText("Haz click aqui");

        GridLayout.LayoutParams paramsb = new GridLayout.LayoutParams();
        paramsb.rowSpec = GridLayout.spec(4); // Fila 4 (no 8)
        paramsb.columnSpec = GridLayout.spec(0, 3); // Ocupa 3 columnas (no 5)
        paramsb.width = GridLayout.LayoutParams.MATCH_PARENT;
        paramsb.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramsb.setMargins(64, 64, 64, 64);

        // Añadir funcionalidad al segundo botón
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarActivity2(v);
            }
        });

        layout.addView(b, paramsb);

        setContentView(layout);
    }

    private void cambiarColor(int fila, int col) {
        // Cambiar color del botón pulsado
        alternarColor(botones[fila][col]);

        // Adyacentes
        if (fila > 0) alternarColor(botones[fila - 1][col]); // arriba
        if (fila < 2) alternarColor(botones[fila + 1][col]); // abajo
        if (col > 0) alternarColor(botones[fila][col - 1]); // izquierda
        if (col < 2) alternarColor(botones[fila][col + 1]); // derecha
    }

    private void alternarColor(Button b) {
        int colorActual = ((ColorDrawable) b.getBackground()).getColor();
        if (colorActual == colorOriginal) {
            b.setBackgroundColor(colorSecundario);
        } else {
            b.setBackgroundColor(colorOriginal);
        }
    }

    private void comprobarVictoria() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                int colorActual = ((ColorDrawable) botones[fila][col].getBackground()).getColor();
                if (colorActual != colorSecundario) {
                    return; // Todavía no ha ganado
                }
            }
        }

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                botones[fila][col].setBackgroundColor(getColor(R.color.RED));
            }
        }

        Toast.makeText(this, "¡Lo logré!", Toast.LENGTH_LONG).show();
    }

    private void reiniciarJuego() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                botones[fila][col].setBackgroundColor(colorOriginal);
            }
        }
    }


    public void iniciarActivity2(View view) {
        Intent intentActivity = new Intent(this, MainActivity2.class);
        intentActivity.putExtra("Contar", contar2);
        intentActivity.putExtra("MiValor2", "nuevo");
        startActivity(intentActivity);
    }
}