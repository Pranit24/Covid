import React, { Component } from "react";
import {
  ComposableMap,
  Geographies,
  Geography,
  ZoomableGroup,
  Marker,
} from "react-simple-maps";
import axios from "axios";
import { scaleQuantile, scaleQuantize } from "d3-scale";
// import { mapJson } from "../maps/ne_10m_admin_0_countries";
// const geoUrl =
//   "https://raw.githubusercontent.com/zcreativelabs/react-simple-maps/master/topojson-maps/world-110m.json";
const mapJson = require("../maps/ne_10m_admin_0_countries.json");

// const colorScale = scaleQuantile()
//   .domain(this.props.data.map((d) => d.confirmed))
//   .range([
//     "#ffedea",
//     "#ffcec5",
//     "#ffad9f",
//     "#ff8a75",
//     "#ff5533",
//     "#e2492d",
//     "#be3d26",
//     "#9a311f",
//     "#782618",
//   ]);

export default class Map extends Component {
  constructor() {
    super();
    this.state = {
      scale: [],
      data: undefined,
    };

    this.colorScale = scaleQuantile()
      .domain([
        0,
        100,
        1000,
        10000,
        100000,
        1000000,
        1500000,
        2000000,
        2500000,
        3000000,
        4000000,
        4500000,
        5000000,
      ])
      .range([
        "#FFFF00",
        "#FFE800",
        "#FFD100",
        "#FFB900",
        "#FFA200",
        "#FF8B00",
        "#FF7400",
        "#FF5D00",
        "#FF4600",
        "#FF2E00",
        "#FF1700",
        "#FF0000",
        // "#E80000",
        // "#D10000",
        // "#B90000",
        // "#A20000",
      ]);
  }

  componentDidMount() {
    this.loadData();
  }

  fetchAllData = async () => {
    const res = await axios("/api/data");
    return await res.data;
  };

  loadData = () => {
    this.fetchAllData().then((data) => {
      for (let i = 0; i < data.length; i++) {
        if (data[i].country === "US") {
          data[i].country = "United States of America";
        }
        console.log(data[i]);
      }
      const scale = data.map((d) => d.confirmed);
      this.setState({ data, scale });
    });
  };

  render() {
    let data = this.state.data;
    return (
      <div>
        <ComposableMap
          width={980}
          height={462.5}
          style={{
            width: "100%",
            height: "100%",
          }}
        >
          <ZoomableGroup>
            <Geographies geography={mapJson}>
              {({ geographies }) =>
                geographies.map((geo) => {
                  let location = "";
                  if (data !== undefined) {
                    location = data.find(
                      (d) => d.country === geo["properties"]["ADMIN"]
                    );
                  }
                  const color = location
                    ? this.colorScale(location.confirmed)
                    : "#DDDD";
                  location
                    ? console.log(
                        color +
                          "=" +
                          geo["properties"]["ADMIN"] +
                          "=" +
                          location.country +
                          ":" +
                          location.confirmed
                      )
                    : console.log(color + "=" + geo["properties"]["ADMIN"]);
                  return (
                    <Geography
                      key={geo.rsmKey}
                      geography={geo}
                      // fill="#"
                      fill={color}
                    />
                  );
                })
              }
            </Geographies>
          </ZoomableGroup>
        </ComposableMap>
      </div>
    );
  }
}
