package com.ldh.heimerdingerslab.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ldh.heimerdingerslab.R;
import com.ldh.heimerdingerslab.databinding.FragmentGalleryBinding;

import java.util.Arrays;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private EditText editText;
    private TextView textView,textView_result;
    private ImageButton imageButton,imageButton_swap;
    private Spinner spinner_input,spinner_result;
    private ArrayAdapter adapter;
    List<String> states = Arrays.asList("Base 2","Base 4","Base 8","Base 10","Base 16");
    String input;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editText = (EditText) root.findViewById(R.id.editText) ;
        textView = (TextView) root.findViewById(R.id.textView);
        textView_result = (TextView) root.findViewById(R.id.textView_result);
        imageButton = (ImageButton) root.findViewById(R.id.img_show_result);
        imageButton_swap = (ImageButton) root.findViewById(R.id.img_swap);
        spinner_input = (Spinner) root.findViewById(R.id.spinner_input);
        spinner_result = (Spinner) root.findViewById(R.id.spinner_result);
        setAdapter();
        setSpinnerInputItem();
        setSpinnerResultItem();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                String check = spinner_input.getSelectedItem().toString();
                String t = "";
                String result = "";
                switch (check) {
                    case "Base 2":
                        t = convertToDecimal(input, 2);
                        break;
                    case "Base 10":
                        t = convertToDecimal(input, 10);
                        break;
                    case "Base 4":
                        t = convertToDecimal(input, 4);
                        break;
                    case "Base 8":
                        t = convertToDecimal(input,8);
                        break;
                    case "Base 16":
                        t = convertToDecimal(input, 16);
                        break;

                }
                if (t.equals("Error"))
                {
                    result = t;
                }
                else {
                    String check_result = spinner_result.getSelectedItem().toString();
                    switch (check_result) {
                        case "Base 2":
                            result = Integer.toBinaryString(Integer.parseInt(t));
                            break;
                        case "Base 8":
                            result = Integer.toOctalString(Integer.parseInt(t));
                            break;
                        case "Base 16":
                            result = Integer.toHexString(Integer.parseInt(t));
                            break;
                        case "Base 4":
                            t = Integer.toBinaryString(Integer.parseInt(t));
                            if (t.length() % 2 != 0) {
                                t = '0' + t;
                            }
                            for (int i = 0; i < t.length(); i = i + 2) {
                                int x = Integer.parseInt(t.substring(i, i + 1)) * 2 + Integer.parseInt(t.substring(i + 1, i + 2));
                                result = result + x + "";
                            }
                            break;
                        case "Base 10":
                            result = t;

                    }
                }

                textView_result.setText(result);

            }
        });
        imageButton_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = spinner_input.getSelectedItem().toString();
                String b = spinner_result.getSelectedItem().toString();
                // ArrayAdapter<String> spinnerAdap = (ArrayAdapter<String>) spinner_input.getAdapter();
                int inputPosition = adapter.getPosition(b);
                int resultPosition = adapter.getPosition(a);
                spinner_input.setSelection(inputPosition);
                spinner_result.setSelection(resultPosition);
            }
        });
        return root;
    }
    private void setAdapter()
    {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
    private void setSpinnerInputItem(){

        spinner_input.setAdapter(adapter);
    }
    private void setSpinnerResultItem(){

        spinner_result.setAdapter(adapter);
    }
    public void showToast(String msg)
    {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    public String convertToDecimal(String t , int base)
    {
        try {
            int x = Integer.parseInt(t,base);
            return x+"";
        }
        catch (Exception e)
        {
            return "Error";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}