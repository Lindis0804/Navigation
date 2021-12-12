package com.ldh.heimerdingerslab.ui.slideshow;

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
import com.ldh.heimerdingerslab.databinding.FragmentSlideshowBinding;

import java.util.Arrays;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel galleryViewModel;
    private FragmentSlideshowBinding binding;
    private EditText editText;
    private TextView textView,textView_result;
    private ImageButton imageButton,imageButton_swap;
    private Spinner spinner_input,spinner_result;
    private ArrayAdapter adapter;
    List<String> states = Arrays.asList("Mm","Cm","Dm","M","Dam","Hm","Km");
    String input;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
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
                double t = Double.parseDouble(input);
                String result = "";
                switch (check) {
                    case "Mm":
                        t = t/1000;
                        break;
                    case "Cm":
                        t = t/100;
                        break;
                    case "Dm":
                        t = t/10;
                        break;
                    case "M":
                        break;
                    case "Dam":
                        t = t*10;
                        break;
                    case "Hm":
                        t = t*100;
                        break;
                    case "Km":
                        t=t*1000;

                }
                    String output = "";
                    String check_result = spinner_result.getSelectedItem().toString();
                    switch (check_result) {
                        case "Mm":
                            output = t*1000+"";
                            break;
                        case "Cm":
                            output = t*100+"";
                            break;
                        case "Dm":
                            output = t*10+"";
                            break;
                        case "M":
                            output = t+"";
                            break;
                        case "Dam":
                            output = t/10+"";
                            break;
                        case "Hm":
                            output = t/100+"";
                            break;
                        case "Km":
                            output = t/1000+"";
                            break;

                    }
                    String list[] = output.split("\\.");
                    if (list[1].equals("0"))
                    {
                        textView_result.setText(list[0]);
                    }
                    else {

                        textView_result.setText(output);
                    }
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