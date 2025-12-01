-- ===================================
--   INSERT INITIAL BOOKINGS
-- ===================================

INSERT INTO booking (
    stay_id,
    user_id,
    start_requested_date,
    end_requested_date,
    status,
    email,
    number
) VALUES
-- Booking 1
(1, '8d75d3af-1066-430c-9a1d-579c8004a563', '2026-01-10', '2026-01-15', 'ACCEPTED', 'test1@example.com', '0612345678'),

-- Booking 2
(2, '8d75d3af-1066-430c-9a1d-579c8004a563', '2026-02-05', '2026-02-12', 'PENDING', 'test2@example.com', '0698765432'),

-- Booking 3
(5, '8d75d3af-1066-430c-9a1d-579c8004a563', '2025-03-01', '2025-03-05', 'REJECTED', 'test3@example.com', '0777888999'),

-- Booking 4
(3, '8d75d3af-1066-430c-9a1d-579c8004a563', '2026-03-20', '2026-03-25', 'PENDING', 'test4@example.com', '0642424242'),

-- Booking 5
(4, '8d75d3af-1066-430c-9a1d-579c8004a563', '2026-04-10', '2026-04-18', 'PENDING', 'contact@client.com', '0600112233');