import React, { Component } from "react";
import PropTypes from "prop-types";
import "antd/dist/antd.css";
import { Table, Tag } from "antd";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import { ArrowUpOutlined, ArrowDownOutlined } from "@ant-design/icons";

export default class ListView extends Component {
  constructor() {
    super();
    this.state = {
      url: "/api/data",
    };
  }
  componentDidMount = () => {
    this.setState({ url: window.location.href });
  };
  const;
  columns = [
    {
      title: "Rank",
      dataIndex: "key",
    },
    {
      title: "Country",
      dataIndex: "country",
      render: (text) => text,
    },
    {
      title: "Confirmed",
      dataIndex: "confirmed",
      render: (text, row) => {
        const diff = (
          <h5 style={{ color: "green" }}>
            <ArrowUpOutlined style={{ color: "green", fontSize: "10px" }} />{" "}
            {row.difference.confirmed}
          </h5>
        );
        console.log(diff);
        return (
          <React.Fragment>
            {diff}
            {text}
          </React.Fragment>
        );
      },
    },
    {
      title: "Deaths",
      dataIndex: "deaths",
      render: (text, row) => {
        const diff = (
          <h5 style={{ color: "green" }}>
            <ArrowUpOutlined style={{ color: "green", fontSize: "10px" }} />{" "}
            {row.difference.deaths}
          </h5>
        );
        console.log(diff);
        return (
          <React.Fragment>
            {diff}
            {text}
          </React.Fragment>
        );
      },
    },
    {
      title: "Recovered",
      dataIndex: "recovered",
      render: (text, row) => {
        const diff = (
          <h5 style={{ color: "green" }}>
            <ArrowUpOutlined style={{ color: "green", fontSize: "10px" }} />{" "}
            {row.difference.recovered}
          </h5>
        );
        return text !== 0 ? (
          <React.Fragment>
            {diff}
            {text}
          </React.Fragment>
        ) : (
          <React.Fragment>Not Available</React.Fragment>
        );
      },
    },
    {
      title: "Active",
      dataIndex: "active",
      render: (text, row) => {
        const diff =
          row.difference.active >= 0 ? (
            <h5 style={{ color: "green" }}>
              <ArrowUpOutlined style={{ color: "green", fontSize: "10px" }} />{" "}
              {row.difference.active}
            </h5>
          ) : (
            <h5 style={{ color: "red" }}>
              <ArrowDownOutlined style={{ color: "red", fontSize: "10px" }} />{" "}
              {row.difference.active}
            </h5>
          );
        console.log(diff);
        return (
          <React.Fragment>
            {diff}
            {text}
          </React.Fragment>
        );
      },
    },
  ];

  render() {
    return (
      <React.Fragment>
        <Table
          columns={this.columns}
          dataSource={this.props.data}
          style={{ whiteSpace: "post" }}
          onRow={(record) => ({
            onDoubleClick: () => {
              !this.props.country
                ? this.props.loadStateData(record.country)
                : console.log("HERE");
            },
          })}
        />
      </React.Fragment>
    );
  }
}
