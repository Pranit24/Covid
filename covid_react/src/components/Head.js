import React, { Component } from "react";
import PropTypes from "prop-types";
import { Menu, Layout, Button } from "antd";
import { Redirect } from "react-router-dom";
import "antd/dist/antd.css";

const { Header, Footer, Sider, Content } = Layout;
export default class Head extends Component {
  constructor() {
    super();
    this.state = {
      redirect: false,
    };
  }

  render() {
    return (
      <React.Fragment>
        <Header className="site-layout-background" style={{ padding: 0 }}>
          <p style={{ color: "white", marginLeft: "25", paddingLeft: 50 }}>
            COVID-19 Live Data
          </p>
        </Header>
      </React.Fragment>
    );
  }
}
