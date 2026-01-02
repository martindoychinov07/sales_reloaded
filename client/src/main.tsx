import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './main/react/App.tsx'
import {BrowserRouter} from "react-router";
import { initClient } from "./main/react/api/ApiProvider.ts";

await initClient();

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>
)
