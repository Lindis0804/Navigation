package com.ldh.heimerdingerslab.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.ldh.heimerdingerslab.OnClickFragments;
import com.ldh.heimerdingerslab.R;
import com.ldh.heimerdingerslab.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private TextView textView_small,textView_big;
    private String answer,input,input_screen,string="";
    private ImageButton imageButton_delete_all, imageButton_square;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textView_big = (TextView) root.findViewById(R.id.result_view);
        textView_small = (TextView) root.findViewById(R.id.expression_view);
        imageButton_square = (ImageButton) root.findViewById(R.id.button_binh_phuong);
        imageButton_delete_all = (ImageButton) root.findViewById(R.id.button_tro_lai);
        imageButtonOnCLick();
        for (int iButton =1;iButton<=22;iButton++)
        {
            int idView = getResources().getIdentifier("button_"+iButton,"id",getContext().getPackageName());
            View eventView = root.findViewById(idView);
            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ButtonClick(view);
                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void convertToInt()
    {
        String n[] = input.split("\\.");
        if (n.length >1)
        {
            if (n[1].equals("0"))
            {
                answer = n[0];
                input = n[0];
            }
        }
    }
    public void imageButtonOnCLick()
    {

        imageButton_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = input.substring(0,input.length()-answer.length());
                answer = Double.parseDouble((answer))*Double.parseDouble(answer)+"";
                String n[] = answer.split("\\.");
                if (n.length >1)
                {
                    if (n[1].equals("0"))
                    {
                        answer = n[0];

                    }
                }
                input = input+answer;
                textView_small.setText(input);
                textView_big.setText(answer);
            }
        });
        imageButton_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = "";
                textView_small.setText(input);
                textView_big.setText(answer);
            }
        });

    }
    public void ButtonClick(final View view)
    {
        Button button = (Button) view;
        String data = button.getText().toString();
        switch (data)
        {
            case "CE":
                string ="";
                input_screen = "";
                input = "";
                answer = "0";
                textView_big.setTextSize(80);
                break;
            case "C":
                if (input.length()>0)
                {
                    string ="";
                    String newText_screen = input_screen.substring(0,input_screen.length()-1);
                    String newText = input.substring(0, input.length() - 1);
                    input= newText;
                    input_screen = newText_screen;

                }

                break;
            case "=":
                string ="";
                solve();
                //  answer = input;
                break;
            case "×":
                string ="";
                solve();
                input_screen += "×";
                input +="×";
                break;
            case "%":
                string = "";
                try {
                    double percent = Double.parseDouble(answer)*0.01;
                    input = input.substring(0,input.length()-answer.length());
                    answer = percent+"";
                    input = input+answer;

                }
                catch(Exception e)
                {
                    System.out.println("Error!");
                }
                break;
            case "1/x":
                string = "";
                if (answer.equals("0"))
                {
                    answer = "Cannot divide by zero";
                    textView_big.setTextSize(30);

                }
                else
                {
                    double phan_so = 1/Double.parseDouble(answer);
                    input = input.substring(0,input.length()-answer.length());
                    answer = phan_so+"";
                    input = input+answer;
                }
                break;
            case "sqrt(x)":
                string = "";
                try {
                    input = input.substring(0,input.length()-answer.length());
                    answer = Math.sqrt(Double.parseDouble((answer)))+"";
                    input = input+answer;
                    convertToInt();
                }
                catch(Exception e)
                {

                }
                break;
            case "+/_":
                string = "";
                input = input.substring(0,input.length()-answer.length());
                answer = -Double.parseDouble(answer)+"";
                input = input+answer;
                convertToInt();
                break;

            default:
                if (input == null)
                {
                    input_screen ="";
                    input = "";
                }
                if (data.equals("+") || data.equals("-") || data.equals("÷"))
                {
                    solve();
                    string ="";
                }
                if (data.equals("0")||data.equals("1")||data.equals("2")||data.equals("3")||data.equals("4")||data.equals("5")||data.equals("6")||data.equals("7")||data.equals("8")||data.equals("9")||data.equals("."))
                {
                    string +=data;
                    answer = string;
                }
                input_screen += data;
                input +=data;

                break;
        }
        textView_small.setText(input);
        textView_big.setText(answer);
    }


    public void solve()
    {
        if (input.split("\\×").length == 2)
        {
            String number[] = input.split("\\×");
            try {
                double mul = Double.parseDouble(number[0])*Double.parseDouble(number[1]);
                //answer = mul+"";
                answer = mul+"";
                input =mul+"";
            }
            catch(Exception e)
            {

            }
        }
        if (input.split("\\÷").length == 2)
        {
            String number[] = input.split("÷");
            try {
                double div = Double.parseDouble(number[0])/Double.parseDouble(number[1]);
                answer = div+"";
                input = div+"";
            }
            catch(Exception e)
            {

            }
        }
        if (input.split("\\+").length == 2)
        {
            String number[] = input.split("\\+");
            try {
                double sum = Double.parseDouble(number[0])+Double.parseDouble(number[1]);
                answer = sum+"";
                input = sum+"";
            }
            catch(Exception e)
            {

            }
        }
        if (input.split("\\-").length == 2)
        {
            String number[] = input.split("\\-");
            try {
                double sub = Double.parseDouble(number[0])-Double.parseDouble(number[1]);
                answer = sub+"";
                input = sub+"";
            }
            catch(Exception e)
            {

            }
        }
        convertToInt();

    }



}