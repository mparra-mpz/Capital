/**
 * Javascript for menu options.
 **/

var MenuBox = React.createClass({

  render: function() {
    return (
      <div>
        <ul>
          <li><a href="/fund/update">Actualizar</a></li>
          <li><a href="/fund">Búsqueda</a></li>
          <li><a href="/fund">Ranking</a></li>
          <li><a href="/fund">Ayuda</a></li>
        </ul>
        <ul>
          <li><a href="https://github.com/mparra-mpz/Capital">GitHub</a></li>
          <li><a href="https://www.fol.cl/">FOL</a></li>
        </ul>
      </div>
    );
  }

});

ReactDOM.render(
  <MenuBox />,
  document.getElementById('menu')
);