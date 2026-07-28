import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import ShowApp from './ShowApp.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ShowApp />
  </StrictMode>,
)
