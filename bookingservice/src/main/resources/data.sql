-- ===================================
--   INSERT INITIAL BOOKINGS
-- ===================================

INSERT INTO booking (
    mission_id,
    user_id,
    start_requested_date,
    end_requested_date,
    status,
    email,
    number
) VALUES
-- Booking 1
(1, 101, '2025-01-10', '2025-01-15', 'PENDING', 'test1@example.com', '0612345678'),

-- Booking 2
(2, 102, '2025-02-05', '2025-02-12', 'CONFIRMED', 'test2@example.com', '0698765432'),

-- Booking 3
(1, 103, '2025-03-01', '2025-03-05', 'CANCELLED', 'test3@example.com', '0777888999'),

-- Booking 4
(3, 101, '2025-03-20', '2025-03-25', 'PENDING', 'test4@example.com', '0642424242'),

-- Booking 5
(4, 105, '2025-04-10', '2025-04-18', 'CONFIRMED', 'contact@client.com', '0600112233');
