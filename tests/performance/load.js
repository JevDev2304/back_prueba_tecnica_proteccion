import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50,          // usuarios virtuales concurrentes
  iterations: 2000, // total de peticiones
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export default function () {
  const res = http.get(`${BASE_URL}/actuator/health`);
  check(res, { 'status 200': (r) => r.status === 200 });
  sleep(0.1);
}

// Ejecutar:
//   k6 run load.js
//   k6 run -e BASE_URL=https://tu-dominio.com load.js
//
// Para el endpoint de suma:
//   const payload = JSON.stringify({ a: 5, b: 3, email: 'test@example.com' });
//   const res = http.post(`${BASE_URL}/api/sum`, payload, { headers: { 'Content-Type': 'application/json' } });
