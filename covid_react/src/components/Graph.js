import React, { Component } from "react";
import PropTypes from "prop-types";
import { ResponsiveBar, Bar } from "@nivo/bar";

export default class Graph extends Component {
  render() {
    const data = this.props.data.slice(0, 10);
    const country =
      this.props.country !== "" ? this.props.country : "Worldwide";
    return (
      <div style={{ height: 625, width: 1200 }}>
        <ResponsiveBar
          data={data}
          keys={["confirmed", "deaths", "recovered", "active"]}
          indexBy="country"
          margin={{ top: 50, right: 130, bottom: 70, left: 80 }}
          padding={0.3}
          colors={{ scheme: "nivo" }}
          defs={[
            {
              id: "dots",
              type: "patternDots",
              background: "inherit",
              color: "#38bcb2",
              size: 4,
              padding: 1,
              stagger: true,
            },
            {
              id: "lines",
              type: "patternLines",
              background: "inherit",
              color: "#eed312",
              rotation: -45,
              lineWidth: 6,
              spacing: 10,
            },
          ]}
          fill={[
            {
              match: {
                id: "fries",
              },
              id: "dots",
            },
            {
              match: {
                id: "sandwich",
              },
              id: "lines",
            },
          ]}
          borderColor={{ from: "color", modifiers: [["darker", 1.6]] }}
          axisTop={{
            tickSize: 0,
            tickPadding: 5,
            tickRotation: 0,
            legend: "COVID-19 " + country + " cases",
            legendPosition: "middle",
            legendOffset: 32,
          }}
          axisRight={null}
          axisBottom={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: "Country",
            legendPosition: "middle",
            legendOffset: 32,
          }}
          axisLeft={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: "Cases",
            legendPosition: "middle",
            legendOffset: -60,
          }}
          labelSkipWidth={12}
          labelSkipHeight={12}
          labelTextColor={{ from: "color", modifiers: [["darker", 1.6]] }}
          legends={[
            {
              dataFrom: "keys",
              anchor: "bottom-right",
              direction: "column",
              justify: false,
              translateX: 120,
              translateY: 0,
              itemsSpacing: 2,
              itemWidth: 100,
              itemHeight: 20,
              itemDirection: "left-to-right",
              itemOpacity: 0.85,
              symbolSize: 20,
              effects: [
                {
                  on: "hover",
                  style: {
                    itemOpacity: 1,
                  },
                },
              ],
            },
          ]}
          animate={true}
        />
      </div>
    );
  }
}
