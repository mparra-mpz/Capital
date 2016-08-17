/**
 * Javascript for update database.
 */

var UpdateDateBox = React.createClass({

  getUpdateDate: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },

  getInitialState: function() {
    return {data: {"year":2016,
                   "month":"JANUARY",
                   "era":"CE",
                   "dayOfMonth":1,
                   "dayOfWeek":"FRIDAY",
                   "dayOfYear":1,
                   "leapYear":true,
                   "monthValue":1,
                   "chronology":{"calendarType":"iso8601","id":"ISO"}}};
  },

  componentDidMount: function() {
    this.getUpdateDate();
    setInterval(this.getUpdateDate, this.props.pollInterval);
  },

  render: function() {
    return (
      <div className="updateDateBox">
        <p>Last database update was: {this.state.data.dayOfMonth} {this.state.data.month} {this.state.data.year}</p>
      </div>
    );
  }

});

ReactDOM.render(
  <UpdateDateBox url="http://localhost:8080/fund/update_date" pollInterval={60000} />,
  document.getElementById('update')
);
