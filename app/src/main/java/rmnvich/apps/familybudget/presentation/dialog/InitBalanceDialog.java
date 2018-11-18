package rmnvich.apps.familybudget.presentation.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.math.BigDecimal;
import java.util.Objects;

import rmnvich.apps.familybudget.R;

import static java.math.BigDecimal.ROUND_DOWN;

public class InitBalanceDialog extends MaterialDialog.Builder implements MaterialDialog.SingleButtonCallback {

    private InitBalanceDialogListener mListener;
    private MaterialEditText mEtBalance;

    @SuppressLint("InflateParams")
    public InitBalanceDialog(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        View view = inflater.inflate(R.layout.balance_dialog, null);
        mEtBalance = view.findViewById(R.id.et_balance);

        this.title(R.string.starting_balance)
                .titleColor(Color.BLACK)
                .customView(view, true)
                .positiveText(R.string.apply)
                .autoDismiss(false)
                .cancelable(false)
                .onPositive(this);
    }

    public void show(InitBalanceDialogListener listener) {
        if (mListener == null) {
            mListener = listener;
        }
        this.show();
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        String balance = mEtBalance.getText().toString();
        if (!balance.isEmpty()) {
            BigDecimal bigDecimalBalance = new BigDecimal(balance)
                    .setScale(2, ROUND_DOWN);

            mListener.onClickApply(bigDecimalBalance.toString());
            dialog.dismiss();
        }
    }

    public interface InitBalanceDialogListener {
        void onClickApply(String balance);
    }
}
