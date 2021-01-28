package com.testalarstudios.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.testalarstudios.R;
import com.testalarstudios.databinding.FragmentDetailsBinding;
import com.testalarstudios.model.DataModel;
import com.testalarstudios.presenter.DetailsFragmentPresenter;
import com.testalarstudios.view.DetailsFragmentView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class DetailsFragment extends MvpAppCompatFragment implements DetailsFragmentView {

    @InjectPresenter
    public DetailsFragmentPresenter presenter;

    private FragmentDetailsBinding vb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vb = FragmentDetailsBinding.bind(view);

        NavigationUI.setupWithNavController(vb.toolbar, Navigation.findNavController(view));

        setData((DataModel) requireArguments().getSerializable("args"));
    }

    @Override
    public void onResume() {
        super.onResume();
        vb.mapView.onResume();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String message) {
        Snackbar.make(vb.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    public void setData(DataModel data) {
        vb.labelId.setText(data.getId());
        vb.labelName.setText(data.getName());
        vb.labelCountry.setText(data.getCountry());
        vb.labelLat.setText(String.valueOf(data.getLat()));
        vb.labelLon.setText(String.valueOf(data.getLon()));

        vb.mapView.setTileSource(TileSourceFactory.MAPNIK);
        vb.mapView.setScrollableAreaLimitLatitude(
                MapView.getTileSystem().getMaxLatitude(),
                MapView.getTileSystem().getMinLatitude(),
                0
        );
        GeoPoint point = new GeoPoint(data.getLat(), data.getLon());
        Marker marker = new Marker(vb.mapView);
        marker.setPosition(point);
        vb.mapView.getOverlays().add(marker);
        vb.mapView.getController().animateTo(point, 18.0, 200L);
    }
}