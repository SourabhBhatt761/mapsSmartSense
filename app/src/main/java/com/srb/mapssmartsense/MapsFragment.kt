package com.srb.mapssmartsense

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Transformations.map
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.srb.mapssmartsense.databinding.ActivityMapsBinding
import com.srb.mapssmartsense.db.AppEntity
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class MapsFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel by viewModels<MapViewModel>()
    private var lat = 0.0
    private var long = 0.0
    private var place : String = "null"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMapsBinding.inflate(layoutInflater)

        onClicks()

        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun showLocation(){
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            val geoCoder = Geocoder(requireContext())
            lat = location.latitude
            long = location.longitude
            val currentLocation = geoCoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
            place = currentLocation.first().subLocality
            Log.d("FirstFragment", currentLocation.first().countryCode)
            Log.d("FirstFragment", currentLocation.first().subLocality)
            Log.d("FirstFragment", location.latitude.toString())
            Log.d("FirstFragment", location.longitude.toString())

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//                val mapFragment = requireActivity().supportFragmentManager
//                    .findFragmentById(R.id.map) as SupportMapFragment
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(lat,long)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in $place"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isScrollGesturesEnabled  = true
        }


    }

    override fun onResume() {
        super.onResume()


        if (hasLocationPermission()) {
            Handler(Looper.getMainLooper()).postDelayed({
                checkLocationIsOn()
            },1000
            )

        } else {
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionDenied(this,perms.first())){
            SettingsDialog.Builder(requireContext()).build().show()
        }else{
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


    private fun onClicks() {

        binding.checkInBtn.setOnClickListener {
            val time = SimpleDateFormat("HH:mm a",Locale.US).format(Date())
            Log.i("uni",time)
            try {

                Log.i("uni",lat.toString())
                Log.i("uni",long.toString())

                viewModel.insertAppData(
                    listOf(AppEntity(
                        id = 0,
                        lat,
                        long,
                        time.toString(),
                            place
                    )
                )
                )

                Log.i("uni",Calendar.getInstance().time.toString())


                Toast.makeText(requireContext(), "Saved $place", Toast.LENGTH_SHORT).show()
            }catch (e : Exception){
                Log.i("uni",lat.toString())
                Log.i("uni",long.toString())
                Log.i("uni",e.printStackTrace().toString())
            }
        }


        binding.checkHistoryBtn.setOnClickListener {
           findNavController().navigate(R.id.historyFragment)
        }




    }

    private fun checkLocationIsOn() {

        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Handler(Looper.getMainLooper()).postDelayed({
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())

                showLocation()
            },500)



        } else {
            AlertDialog.Builder(requireContext())
                .setMessage(" Location is off , Would you like to turn it on?")
                .setPositiveButton("Yes") { dialog, _ ->
                    requireActivity().startActivity(
                        Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                    dialog.dismiss()

                }
                .setNegativeButton(" No") { dialog, _ ->
                dialog.dismiss()
                }
                .setCancelable(true)
                .create()
                .show()
        }
    }

}