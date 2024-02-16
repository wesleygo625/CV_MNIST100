/**
 * This file contains implementations for methods in the Page class.
 *
 * You'll need to add code here to make the corresponding tests pass.
 */

#include "page/page.h"

using namespace std;

// Ensure PAGE_SIZE is initialized.
const size_t Page::PAGE_SIZE;

Page* Page::read_from_input(std::istream& in) {
    // Read at most PAGE_SIZE bytes into a buffer.
    char bytes_buffer[PAGE_SIZE];
    in.read(bytes_buffer, PAGE_SIZE);

    // If nothing was read, return a null pointer.
    if (in.gcount() == 0) {
        return nullptr;
    }

    // Create a vector of characters for simplicity.
    vector<char> bytes = vector<char>(bytes_buffer, bytes_buffer + in.gcount());

    return new Page(bytes);
}


size_t Page::size() const
{
    return bytes.size();
}


bool Page::is_valid_offset(size_t offset) const
{
    if (offset >= 0 && offset <= bytes.size() - 1) {
        return true;
    }
    return false;
}


char Page::get_byte_at_offset(size_t offset)
{
    return bytes.at(offset);
}
