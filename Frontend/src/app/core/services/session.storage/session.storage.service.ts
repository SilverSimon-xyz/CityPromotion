import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  setItem(key: string, value: any): void {
    if(this.isBrowser) {
      sessionStorage.setItem(key, JSON.stringify(value));
    }
  }

  getItem(key: string): string | null {
    if(this.isBrowser) {
      const item = sessionStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    }
    return null;
  }

  removeItem(key: string): void {
    if(this.isBrowser) {
      sessionStorage.removeItem(key);
    }
  }

  clear(): void {
    if(this.isBrowser) {
      sessionStorage.clear();
    }
  }
}
