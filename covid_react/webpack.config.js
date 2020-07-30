var path = require("path");
// const json = require("./file.json");
module.exports = {
  entry: "./src/index.js",
  mode: "development",
  devServer: {
    publicPath: "/",
    contentBase: "./public",
    hot: true,
    proxy: {
      "/": {
        target: "http://localhost:8080/",
        secure: false,
        changeOrigin: true,
      },
    },
  },
  output: {
    path: __dirname,
    filename: "../src/main/resources/static/built/bundle.js",
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /(node_modules)/,
        use: [
          {
            loader: "babel-loader",
            options: {
              presets: [
                "@babel/preset-env",
                "@babel/preset-react",
                {
                  plugins: [
                    "@babel/plugin-proposal-class-properties",
                    "@babel/plugin-transform-runtime",
                  ],
                },
              ],
            },
          },
        ],
      },
      {
        test: /\.svg$/,
        loader: "svg-inline-loader",
      },

      {
        test: /\.css$/i,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
};

// var path = require("path");
// const HtmlWebPackPlugin = require("html-webpack-plugin");

// module.exports = {
//   entry: "./src/index.js",
//   devtool: "sourcemaps",
//   cache: true,
//   mode: "production",
//   output: {
//     path: __dirname,
//     filename: "../src/main/resources/static/built/bundle.js",
//   },
//   module: {
//     rules: [
//       {
//         test: /\.(js|jsx)$/,
//         exclude: /(node_modules)/,
//         use: [
//           {
//             loader: "babel-loader",
//             options: {
//               presets: [
//                 "@babel/preset-env",
//                 "@babel/preset-react",
//                 {
//                   plugins: ["@babel/plugin-proposal-class-properties"],
//                 },
//               ],
//             },
//           },
//         ],
//       },
//       {
//         test: /\.svg$/,
//         loader: "svg-inline-loader",
//       },

//       {
//         test: /\.css$/i,
//         use: ["style-loader", "css-loader"],
//       },
//       {
//         test: /\.html$/,
//         use: [
//           {
//             loader: "html-loader",
//           },
//         ],
//       },
//     ],
//   },
//   plugins: [
//     new HtmlWebPackPlugin({
//       template: "./public/index.html",
//       filename: "../src/main/resources/static/built/index.html",
//     }),
//   ],
// };
