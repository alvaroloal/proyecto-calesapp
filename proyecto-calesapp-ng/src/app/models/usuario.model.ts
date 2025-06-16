export interface Usuario {
    id: string;
    username: string;
    email: string;
    password?: string;
    enabled: boolean;
    rol: 'ROLE_USER' | 'ROLE_ADMIN';
}