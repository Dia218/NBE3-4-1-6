import './App.css';
import axios from 'axios';


function selectData() {
    axios.get('/seller/products')
        .then(function (res) {
            console.log(res)
            console.log("통신 성공")
            if (Array.isArray(res.data.content) && res.data.content.length === 0) console.log("아무것도 안왔어요.")
        });
}

function App() {

    return (
        <div className="App">
            <header className="App-header">
                <div>
                    <button onClick={() => selectData()}>조회</button>
                </div>
            </header>
        </div>
    );
}

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

export default App;
