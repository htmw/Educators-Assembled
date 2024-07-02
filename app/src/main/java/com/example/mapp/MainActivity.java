package com.example.mapp;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Interpreter tflite;
    private EditText inputText;
    private TextView outputText;
    private Button classifyButton;
    private Map<String, Integer> vocab;
    private static final String TAG = "MainActivity";
    private static final int MAX_INPUT_LENGTH = 50;  // 模型期望的输入长度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        outputText = findViewById(R.id.output_text);
        classifyButton = findViewById(R.id.classify_button);

        try {
            tflite = new Interpreter(loadModelFile());
            Log.d(TAG, "Model loaded successfully");
            vocab = loadVocab("vocab.txt");
            if (vocab == null || vocab.isEmpty()) {
                Log.e(TAG, "Vocab is null or empty");
            } else {
                Log.d(TAG, "Vocab loaded successfully");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading model or vocab", e);
        }

        classifyButton.setOnClickListener(v -> classifyText());
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getAssets().openFd("math_problem_classifier.tflite").getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = getAssets().openFd("math_problem_classifier.tflite").getStartOffset();
        long declaredLength = getAssets().openFd("math_problem_classifier.tflite").getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private Map<String, Integer> loadVocab(String vocabFilePath) {
        Map<String, Integer> vocab = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(vocabFilePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    vocab.put(parts[0], Integer.parseInt(parts[1]));
                } else {
                    Log.e(TAG, "Incorrect vocab line format: " + line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading vocab file", e);
            return null;
        }
        return vocab;
    }

    private void classifyText() {
        if (vocab == null) {
            Log.e(TAG, "Vocab is not loaded");
            outputText.setText("Error loading vocab");
            return;
        }

        String text = inputText.getText().toString();
        int[] input = tokenizeText(text, vocab);
        float[][] floatInput = new float[1][MAX_INPUT_LENGTH];  // 模型期望的输入形状
        for (int i = 0; i < MAX_INPUT_LENGTH; i++) {
            if (i < input.length) {
                floatInput[0][i] = input[i];
            } else {
                floatInput[0][i] = 0;  // 填充0
            }
        }

        float[][] output = new float[1][4];  // 模型的输出形状
        tflite.run(floatInput, output);

        int maxIndex = -1;
        float maxValue = -1;
        for (int i = 0; i < output[0].length; i++) {
            if (output[0][i] > maxValue) {
                maxValue = output[0][i];
                maxIndex = i;
            }
        }

        String[] classes = {"Algebra","Counting & Probability","Geometry","Precalculus"};  // 替换为你的实际类别
        outputText.setText(classes[maxIndex]);
    }

    private int[] tokenizeText(String text, Map<String, Integer> vocab) {
        String[] words = text.split(" ");
        int[] tokens = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            tokens[i] = vocab.getOrDefault(words[i], 0);  // 使用0替换无效的索引
        }
        return tokens;
    }
}