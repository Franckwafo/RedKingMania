package org.calma.redkingmania.modal;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.calma.redkingmania.R;

public class Modal_QrScanner extends DialogFragment {

    public static Modal_QrScanner newInstance() {
        return new Modal_QrScanner();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Optionnel : afficher une vue vide
        return new View(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toast.makeText(requireContext(), R.string.mini_game_dev_msg, Toast.LENGTH_SHORT).show();
        dismiss(); // ferme immédiatement le modal
    }
}
