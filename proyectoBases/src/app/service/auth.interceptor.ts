import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const token = authService.getToken();

  console.log('INTERCEPTOR: URL=', req.url, 'Token=', token ? 'SÍ' : 'NO');

  const isValidToken =
    token &&
    token !== 'null' &&
    token !== 'undefined' &&
    token.trim() !== '';

  const isLoginRequest = req.url.includes('/auth/login') || req.url.includes('/login');

  if (isValidToken && !isLoginRequest) {

    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    console.log('Token añadido a:', req.url);
    return next(authReq);
  }

  console.log('Sin token para:', req.url);
  return next(req);
};
