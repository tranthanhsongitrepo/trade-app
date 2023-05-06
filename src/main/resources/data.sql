INSERT INTO trade_app.tbl_user_role(user_role_id, role_name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_STAFF')
ON CONFLICT DO NOTHING;

INSERT INTO trade_app.tbl_user_authority(user_authority_id, user_authority_name)
VALUES (1, 'STAFF_READ'),
       (2, 'STAFF_WRITE'),
       (3, 'STAFF_DELETE'),
       (4, 'USER_READ'),
       (5, 'USER_WRITE'),
       (6, 'USER_DELETE')
ON CONFLICT DO NOTHING;

INSERT INTO trade_app.tbl_user_role_user_authorities(user_authority_id, user_role_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (1, 3),
       (2, 3),
       (3, 3)
ON CONFLICT DO NOTHING;


INSERT INTO trade_app.tbl_users(dtype, user_id, current_access_tokenuuid, email, name, password, phone_number, provider,
                                provider_id, affiliate_code, password_reset_request_id)
VALUES ('USER', 1, null, 'admin@admin.com', 'Admin', '$2a$10$YU6n2s7OK0ojytO6z7kEjOlR7oQron3Bh43yTppOiPvgGPbxqEYvy', '',
        'LOCAL', null, null, null)
ON CONFLICT DO NOTHING;

INSERT INTO trade_app.tbl_user_with_role(user_role_id, user_with_role_user_id)
VALUES (2, 1)
ON CONFLICT DO NOTHING;