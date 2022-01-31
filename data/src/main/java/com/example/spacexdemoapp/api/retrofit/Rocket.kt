package com.example.spacexdemoapp.api.retrofit

import com.google.gson.annotations.SerializedName

data class Rocket(
  @SerializedName("height") val height: Height,
  @SerializedName("diameter") val diameter: Diameter,
  @SerializedName("mass") val mass: Mass,
  @SerializedName("first_stage") val firstStage: FirstStage,
  @SerializedName("second_stage") val secondStage: SecondStage,
  @SerializedName("engines") val engines: Engines,
  @SerializedName("landing_legs") val landingLegs: LandingLegs,
  @SerializedName("payload_weights") val payloadWeights: ArrayList<PayloadWeights>,
  @SerializedName("flickr_images") val flickrImages: ArrayList<String>,
  @SerializedName("name") val name: String,
  @SerializedName("type") val type: String,
  @SerializedName("active") val active: Boolean,
  @SerializedName("stages") val stages: Int,
  @SerializedName("boosters") val boosters: Int,
  @SerializedName("cost_per_launch") val costPerLaunch: Int,
  @SerializedName("success_rate_pct") val successRatePct: Int,
  @SerializedName("first_flight") val firstFlight: String,
  @SerializedName("country") val country: String,
  @SerializedName("company") val company: String,
  @SerializedName("wikipedia") val wikipedia: String,
  @SerializedName("description") val description: String,
  @SerializedName("id") val id: String,

  )


data class CompositeFairing(

  @SerializedName("height") val height: Height,
  @SerializedName("diameter") val diameter: Diameter,

  )

data class Diameter(

  @SerializedName("meters") val meters: Double,
  @SerializedName("feet") val feet: Double,

  )

data class Engines(

  @SerializedName("isp") val isp: Isp,
  @SerializedName("thrust_sea_level") val thrustSeaLevel: ThrustSeaLevel,
  @SerializedName("thrust_vacuum") val thrustVacuum: ThrustVacuum,
  @SerializedName("number") val number: Int,
  @SerializedName("type") val type: String,
  @SerializedName("version") val version: String,
  @SerializedName("layout") val layout: String,
  @SerializedName("engine_loss_max") val engineLossMax: Int,
  @SerializedName("propellant_1") val propellant1: String,
  @SerializedName("propellant_2") val propellant2: String,
  @SerializedName("thrust_to_weight") val thrustToWeight: Float,

  )

data class FirstStage(

  @SerializedName("thrust_sea_level") val thrustSeaLevel: ThrustSeaLevel,
  @SerializedName("thrust_vacuum") val thrustVacuum: ThrustVacuum,
  @SerializedName("reusable") val reusable: Boolean,
  @SerializedName("engines") val engines: Int,
  @SerializedName("fuel_amount_tons") val fuelAmountTons: Double,
  @SerializedName("burn_time_sec") val burnTimeSec: Int,

  )

data class Height(

  @SerializedName("meters") val meters: Double,
  @SerializedName("feet") val feet: Double,

  )

data class Isp(

  @SerializedName("sea_level") val seaLevel: Int,
  @SerializedName("vacuum") val vacuum: Int,

  )

data class LandingLegs(

  @SerializedName("number") val number: Int,
  @SerializedName("material") val material: String,

  )

data class Mass(

  @SerializedName("kg") val kg: Int,
  @SerializedName("lb") val lb: Int,

  )

data class Payloads(

  @SerializedName("composite_fairing") val compositeFairing: CompositeFairing,
  @SerializedName("option_1") val option1: String,

  )

data class PayloadWeights(

  @SerializedName("id") val id: String,
  @SerializedName("name") val name: String,
  @SerializedName("kg") val kg: Int,
  @SerializedName("lb") val lb: Int,

  )

data class SecondStage(

  @SerializedName("thrust") val thrust: Thrust,
  @SerializedName("payloads") val payloads: Payloads,
  @SerializedName("reusable") val reusable: Boolean,
  @SerializedName("engines") val engines: Int,
  @SerializedName("fuel_amount_tons") val fuelAmountTons: Double,
  @SerializedName("burn_time_sec") val burnTimeSec: Int,

  )

data class Thrust(

  @SerializedName("kN") val kN: Int,
  @SerializedName("lbf") val lbf: Int,

  )

data class ThrustSeaLevel(

  @SerializedName("kN") val kN: Int,
  @SerializedName("lbf") val lbf: Int,

  )

data class ThrustVacuum(

  @SerializedName("kN") val kN: Int,
  @SerializedName("lbf") val lbf: Int,

  )